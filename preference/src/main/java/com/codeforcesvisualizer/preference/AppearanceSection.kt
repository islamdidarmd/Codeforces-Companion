package com.codeforcesvisualizer.preference

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codeforcesvisualizer.core.data.components.HeightSpacer
import com.codeforcesvisualizer.domain.entity.UiThemeMode

@Composable
fun AppearanceSection(
    modifier: Modifier = Modifier,
    themeMode: UiThemeMode,
    onThemeModeChanged: (UiThemeMode) -> Unit
) {
    Column(Modifier.padding(16.dp)) {
        Text(text = "Appearance", style = MaterialTheme.typography.subtitle1)
        HeightSpacer(height = 16.dp)

        Text(text = "Theme", style = MaterialTheme.typography.body2)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RadioButton(selected = themeMode == UiThemeMode.Light, onClick = { onThemeModeChanged(UiThemeMode.Light) })
            Text(text = "Light Mode")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RadioButton(selected = themeMode == UiThemeMode.Dark, onClick = { onThemeModeChanged(UiThemeMode.Dark) })
            Text(text = "Dark Mode")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RadioButton(selected = themeMode == UiThemeMode.System, onClick = { onThemeModeChanged(UiThemeMode.System) })
            Text(text = "System Settings")
        }
    }
}