package com.codeforcesvisualizer.core.platform

import androidx.compose.runtime.Composable

data class CalendarEvent(
    val title: String,
    val startTimeMillis: Long,
    val durationMillis: Long,
    val description: String = "",
)

@Composable
expect fun rememberCalendarLauncher(): (CalendarEvent) -> Unit
