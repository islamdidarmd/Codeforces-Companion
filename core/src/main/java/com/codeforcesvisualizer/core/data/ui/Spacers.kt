package com.codeforcesvisualizer.core.data.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun HeightSpacer(modifier: Modifier = Modifier, height: Dp) {
    Spacer(modifier = modifier.height(height = height))
}

@Composable
fun WidthSpacer(modifier: Modifier = Modifier, width: Dp) {
    Spacer(modifier = modifier.width(width = width))
}