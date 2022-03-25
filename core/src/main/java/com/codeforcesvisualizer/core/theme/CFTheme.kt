package com.codeforcesvisualizer.core.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun CFTheme(
    isDarkTheme: Boolean,
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colors = if (isDarkTheme) CFDarkColors else CFLightColors,
        typography = CFTypography,
        content = content
    )
}