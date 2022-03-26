package com.codeforcesvisualizer.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Chip(
    modifier: Modifier = Modifier,
    label: String,
    icon: ImageVector? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .defaultMinSize(minHeight = 24.dp)
            .clip(shape = CircleShape)
            .background(color = MaterialTheme.colors.primary.copy(alpha = 0.1f))
            .clickable(enabled = onClick != null) { onClick?.invoke() }
            .padding(PaddingValues(horizontal = 16.dp, vertical = 4.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = icon,
                contentDescription = null
            )
        }
        WidthSpacer(width = 4.dp)
        Text(text = label, style = MaterialTheme.typography.body2)
    }
}

@Preview
@Composable
private fun Preview() {
    Column {
        Chip(label = "Chip", icon = Icons.Default.LocationOn)
    }
}