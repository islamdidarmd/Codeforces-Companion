package com.codeforcesvisualizer.preference

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeforcesvisualizer.core.EventLogger
import com.codeforcesvisualizer.core.components.CFAppBar

@Composable
fun PreferenceScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    themeManagerViewModel: ThemeManagerViewModel
) {
    val themeModeUiState by themeManagerViewModel.themeModeFlow.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            CFAppBar(
                title = stringResource(R.string.preferences),
                onNavigateBack = onNavigateBack
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                AppearanceSection(
                    themeMode = themeModeUiState.themeMode,
                    onThemeModeChanged = { selectedThemeMode ->
                        themeManagerViewModel.setUiThemeMode(selectedThemeMode)
                        EventLogger.logEvent(
                            event = "Theme Changed",
                            param = bundleOf(
                                "ThemeMode" to selectedThemeMode.toString()
                            )
                        )
                    }
                )
            }
            item {
                Divider()
            }
            item {
                OtherSection()
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PreferenceScreen(
        onNavigateBack = {},
        themeManagerViewModel = hiltViewModel()
    )
}