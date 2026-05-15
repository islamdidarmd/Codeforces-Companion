package com.codeforcesvisualizer.core.platform

import android.content.Intent
import android.provider.CalendarContract
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberCalendarLauncher(): (CalendarEvent) -> Unit {
    val context = LocalContext.current
    return { event ->
        val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, event.title)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, event.startTimeMillis)
            putExtra(CalendarContract.EXTRA_EVENT_END_TIME, event.startTimeMillis + event.durationMillis)
            putExtra(CalendarContract.Events.DESCRIPTION, event.description)
        }
        context.startActivity(intent)
    }
}
