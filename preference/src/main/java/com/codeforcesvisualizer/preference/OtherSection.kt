package com.codeforcesvisualizer.preference

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codeforcesvisualizer.core.data.components.HeightSpacer

@Composable
fun OtherSection(
    modifier: Modifier = Modifier
) {
    Column(Modifier.padding(16.dp)) {
        Text(text = "Others", style = MaterialTheme.typography.subtitle1)
        HeightSpacer(height = 16.dp)

        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "Give Feedback")
        }

        TextButton(onClick = { /*TODO*/ }) {
            Text(text = "Rate App")
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Current Version: 1.0.2",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2
        )
    }
}

@Preview
@Composable
private fun Preview() {
    OtherSection()
}