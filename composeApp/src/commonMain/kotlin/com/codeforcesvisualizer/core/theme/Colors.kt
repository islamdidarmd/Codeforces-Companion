package com.codeforcesvisualizer.core.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

val Blue400 = Color(0xFF63A4FF)
val Blue700 = Color(0xFF1976d2)
val Blue900 = Color(0xFF004ba0)

val Yellow400 = Color(0xFFFFF263)
val Yellow700 = Color(0xFFFBC02D)
val Yellow800 = Color(0xFFC49000)

val White50 = Color(0xFFFFFFFF)
val Black900 = Color(0xFF000000)

internal val CFLightColorScheme = lightColorScheme(
    primary = Blue700,
    onPrimary = White50,
    secondary = Yellow700,
    onSecondary = Black900,
    surface = White50,
    onSurface = Black900
).withBrandedSurface()

internal val CFDarkColorScheme = darkColorScheme(
    primary = Blue400,
    onPrimary = Black900,
    secondary = Yellow400,
    onSecondary = Black900,
    surface = Blue900,
    onSurface = White50
).withBrandedSurface()

internal fun ColorScheme.withBrandedSurface() = copy(
    surface = primary.copy(alpha = 0.08f).compositeOver(surface),
)