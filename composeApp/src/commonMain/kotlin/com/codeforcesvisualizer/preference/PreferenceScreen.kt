package com.codeforcesvisualizer.preference

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import codeforces_visualizer.composeapp.generated.resources.Res
import codeforces_visualizer.composeapp.generated.resources.preferences
import com.codeforcesvisualizer.core.EventLogger
import com.codeforcesvisualizer.core.components.CFAppBar
import com.codeforcesvisualizer.shared.domain.entity.UiThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PreferenceScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    themeManager: ThemeManager = koinViewModel<ThemeManagerViewModel>()
) {
    val themeModeUiState by themeManager.themeModeFlow.collectAsState()
    val rateAppHandler = rememberRateAppHandler()

    Scaffold(
        modifier = modifier,
        topBar = {
            CFAppBar(
                title = stringResource(Res.string.preferences),
                onNavigateBack = onNavigateBack
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                AppearanceSection(
                    themeMode = themeModeUiState.themeMode,
                    onThemeModeChanged = { selectedThemeMode ->
                        themeManager.setUiThemeMode(selectedThemeMode)
                        EventLogger.logEvent(
                            event = "Theme Changed",
                            param = mapOf(
                                "ThemeMode" to selectedThemeMode.toString()
                            )
                        )
                    }
                )
            }
            item {
                HorizontalDivider()
            }
            item {
                OtherSection(rateAppHandler = rateAppHandler)
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PreferenceScreen(
        onNavigateBack = {},
        themeManager = PreviewThemeManager()
    )
}

private class PreviewThemeManager : ThemeManager {
    private val state = MutableStateFlow(ThemeModeUiState())
    override val themeModeFlow: StateFlow<ThemeModeUiState> = state

    override fun setUiThemeMode(themeMode: UiThemeMode) {
        state.value = state.value.copy(themeMode = themeMode)
    }
}