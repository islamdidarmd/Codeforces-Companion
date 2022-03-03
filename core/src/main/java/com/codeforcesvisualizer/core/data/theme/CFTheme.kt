package com.codeforcesvisualizer.core.data.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun CFTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (isSystemInDarkTheme()) CFDarkColors else CFLightColors,
        typography = CFTypography,
        content = content
    )
}