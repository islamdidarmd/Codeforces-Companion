package com.codeforcesvisualizer.core.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
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

internal val CFLightColors = lightColors(
    primary = Blue700,
    primaryVariant = Blue900,
    secondary = Yellow700,
    secondaryVariant = Yellow800,

    onPrimary = White50,
    onSecondary = Black900
)

internal val CFDarkColors = darkColors(
    primary = Blue400,
    primaryVariant = Blue400,
    secondary = Yellow400,
    secondaryVariant = Yellow400,

    onPrimary = Black900,
    onSecondary = Black900,
).withBrandedSurface()

internal fun Colors.withBrandedSurface() = copy(
    surface = primary.copy(alpha = 0.08f).compositeOver(this.surface),
)