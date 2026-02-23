package com.codeforcesvisualizer.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun CFTheme(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = if (isDarkTheme) CFDarkColorScheme else CFLightColorScheme,
        typography = CFTypography,
        content = content
    )
}