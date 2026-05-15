package com.codeforcesvisualizer.core.platform

import androidx.compose.runtime.Composable
import kotlinx.cinterop.ExperimentalForeignApi
import platform.EventKit.EKEntityType
import platform.EventKit.EKEventStore
import platform.EventKit.EKEvent
import platform.EventKit.EKSpan
import platform.Foundation.NSDate
import platform.Foundation.dateWithTimeIntervalSince1970

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun rememberCalendarLauncher(): (CalendarEvent) -> Unit {
    return { event ->
        val store = EKEventStore()
        store.requestAccessToEntityType(
            EKEntityType.EKEntityTypeEvent
        ) { granted, _ ->
            if (granted) {
                val ekEvent = EKEvent.eventWithEventStore(store)
                ekEvent.title = event.title
                ekEvent.startDate = NSDate.dateWithTimeIntervalSince1970(event.startTimeMillis / 1000.0)
                ekEvent.endDate = NSDate.dateWithTimeIntervalSince1970((event.startTimeMillis + event.durationMillis) / 1000.0)
                ekEvent.notes = event.description
                ekEvent.calendar = store.defaultCalendarForNewEvents
                try {
                    store.saveEvent(ekEvent, EKSpan.EKSpanThisEvent, error = null)
                } catch (_: Exception) {}
            }
        }
    }
}
