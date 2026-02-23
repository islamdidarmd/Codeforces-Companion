package com.codeforcesvisualizer.preference

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import codeforces_visualizer.composeapp.generated.resources.Res
import codeforces_visualizer.composeapp.generated.resources.appearance
import codeforces_visualizer.composeapp.generated.resources.dark_mode
import codeforces_visualizer.composeapp.generated.resources.light_mode
import codeforces_visualizer.composeapp.generated.resources.system_settings
import codeforces_visualizer.composeapp.generated.resources.theme
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.shared.domain.entity.UiThemeMode
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppearanceSection(
    modifier: Modifier = Modifier,
    themeMode: UiThemeMode,
    onThemeModeChanged: (UiThemeMode) -> Unit
) {
    Column(modifier.padding(16.dp)) {
        Text(text = stringResource(Res.string.appearance), style = MaterialTheme.typography.titleMedium)
        HeightSpacer(height = 16.dp)

        Text(text = stringResource(Res.string.theme), style = MaterialTheme.typography.bodyMedium)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RadioButton(selected = themeMode == UiThemeMode.Light, onClick = { onThemeModeChanged(UiThemeMode.Light) })
            Text(text = stringResource(Res.string.light_mode))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RadioButton(selected = themeMode == UiThemeMode.Dark, onClick = { onThemeModeChanged(UiThemeMode.Dark) })
            Text(text = stringResource(Res.string.dark_mode))
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RadioButton(selected = themeMode == UiThemeMode.System, onClick = { onThemeModeChanged(UiThemeMode.System) })
            Text(text = stringResource(Res.string.system_settings))
        }
    }
}