package com.codeforcesvisualizer.core.data.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier
            .defaultMinSize(
                minHeight = 24.dp
            ),
        shape = RoundedCornerShape(percent = 50),
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = MaterialTheme.colors.primary.copy(alpha = 0.1f),
            contentColor = MaterialTheme.colors.onSurface
        ),
        contentPadding = PaddingValues(horizontal = 20.dp),
        onClick = onClick
    ) {
        Text(text = label)
    }
}

@Preview
@Composable
fun Preview() {
    Column {
        Chip(label = "Chip") {

        }
    }
}