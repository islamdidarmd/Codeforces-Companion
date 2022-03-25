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

@Composable
fun AppearanceSection(
    modifier: Modifier = Modifier
) {
    Column(Modifier.padding(16.dp)) {
        Text(text = "Appearance", style = MaterialTheme.typography.subtitle1)
        HeightSpacer(height = 16.dp)

        Text(text = "Theme", style = MaterialTheme.typography.body2)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RadioButton(selected = false, onClick = { /*TODO*/ })
            Text(text = "Light Mode")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RadioButton(selected = false, onClick = { /*TODO*/ })
            Text(text = "Dark Mode")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RadioButton(selected = true, onClick = { /*TODO*/ })
            Text(text = "System Settings")
        }
    }
}