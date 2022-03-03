package com.codeforcesvisualizer.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.compositeOver

@Composable
fun CFTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (isSystemInDarkTheme()) CFDarkColors else CFLightColors,
        content = content
    )
}

private val CFLightColors = lightColors(
    primary = Blue700,
    primaryVariant = Blue900,
    secondary = Yellow600,
    secondaryVariant = Yellow700,

    onPrimary = White50,
    onSecondary = Black900
)

private val CFDarkColors = darkColors(
    primary = Blue400,
    primaryVariant = Blue400,
    secondary = Yellow300,
    secondaryVariant = Yellow300,

    onPrimary = Black900,
    onSecondary = Black900,
).withBrandedSurface()

internal fun Colors.withBrandedSurface() = copy(
    surface = primary.copy(alpha = 0.08f).compositeOver(this.surface),
)