package com.codeforcesvisualizer.core.data.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
                minHeight = 28.dp
            ),
        shape = RoundedCornerShape(50),
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary),
        onClick = onClick
    ) {
        Text(text = label)
    }
}

@Composable
fun SelectedChip(
    modifier: Modifier = Modifier,
    label: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier.defaultMinSize(
            minHeight = 28.dp
        ),
        shape = RoundedCornerShape(50),
        contentPadding = PaddingValues(0.dp),
        onClick = onClick,
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
        Spacer(modifier = Modifier.height(8.dp))
        SelectedChip(label = "Chip") {

        }
    }
}