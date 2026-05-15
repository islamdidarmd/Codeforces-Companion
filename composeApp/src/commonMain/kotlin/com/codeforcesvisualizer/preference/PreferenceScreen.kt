package com.codeforcesvisualizer.preference

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeforcesvisualizer.core.EventLogger
import com.codeforcesvisualizer.core.components.CFCard
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.core.components.ScreenHeader
import com.codeforcesvisualizer.core.components.WidthSpacer
import com.codeforcesvisualizer.core.theme.CFThemeColors
import com.codeforcesvisualizer.shared.domain.entity.UiThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PreferenceScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    themeManager: ThemeManager = koinViewModel<ThemeManagerViewModel>()
) {
    val colors = CFThemeColors.current
    val themeModeUiState by themeManager.themeModeFlow.collectAsState()
    val rateAppHandler = rememberRateAppHandler()
    var showStoreError by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colors.bg)
            .padding(horizontal = 18.dp),
    ) {
        item {
            ScreenHeader(
                prompt = "settings",
                title = "Settings",
            )
        }

        // Theme picker
        item {
            CFCard(title = "appearance") {
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
        }

        item { HeightSpacer(height = 16.dp) }

        // Preferences list
        item {
            CFCard(title = "preferences", contentPadding = 0.dp) {
                PreferenceRow(label = "version", value = rateAppHandler.versionName)
                HorizontalDivider(color = colors.border, thickness = 1.dp)
                PreferenceRow(
                    label = "rate app",
                    value = "open store",
                    isAction = true,
                    onClick = {
                        showStoreError = !rateAppHandler.openStore()
                    },
                )
                if (showStoreError) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 8.dp),
                    ) {
                        Text(
                            text = "store not found",
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 10.sp,
                                color = colors.red,
                            ),
                        )
                    }
                }
                HorizontalDivider(color = colors.border, thickness = 1.dp)
                PreferenceRow(label = "theme mode", value = themeModeLabel(themeModeUiState.themeMode))
            }
        }

        item { HeightSpacer(height = 24.dp) }

        // Build info
        item {
            Text(
                text = "cf-visualizer v${rateAppHandler.versionName}",
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    color = colors.dim,
                ),
                textAlign = TextAlign.Center,
            )
        }

        item { HeightSpacer(height = 24.dp) }
    }
}

@Composable
private fun PreferenceRow(
    label: String,
    value: String,
    isAction: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    val colors = CFThemeColors.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (onClick != null) Modifier.background(colors.surface)
                    .let { mod ->
                        @Suppress("DEPRECATION")
                        mod
                    }
                else Modifier
            )
            .padding(horizontal = 14.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                color = colors.fg,
            ),
        )
        Text(
            text = value,
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 12.sp,
                color = if (isAction) colors.violet else colors.dim,
            ),
            modifier = if (onClick != null) {
                Modifier.let { mod ->
                    @Suppress("DEPRECATION")
                    mod
                }
            } else Modifier,
        )
    }
}

private fun themeModeLabel(mode: UiThemeMode): String {
    return when (mode) {
        UiThemeMode.Dark -> "dark"
        UiThemeMode.Light -> "light"
        UiThemeMode.System -> "system"
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
