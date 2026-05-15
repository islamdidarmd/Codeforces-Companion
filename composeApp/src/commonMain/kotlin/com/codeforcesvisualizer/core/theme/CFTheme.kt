package com.codeforcesvisualizer.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun CFTheme(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val extendedColors = if (isDarkTheme) DarkExtendedColors else LightExtendedColors

    CompositionLocalProvider(LocalCFColors provides extendedColors) {
        MaterialTheme(
            colorScheme = if (isDarkTheme) CFDarkColorScheme else CFLightColorScheme,
            typography = CFTypography,
            content = content
        )
    }
}

object CFThemeColors {
    val current: CFExtendedColors
        @Composable get() = LocalCFColors.current
}
