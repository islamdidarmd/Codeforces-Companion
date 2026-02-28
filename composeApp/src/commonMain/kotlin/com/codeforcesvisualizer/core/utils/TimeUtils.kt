package com.codeforcesvisualizer.core.utils

import com.codeforcesvisualizer.core.DefaultAppDateFormat
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

fun Int.convertToDHMS(): String {
    val days = this / (3600 * 24)
    val hours = (this % (3600 * 24)) / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    return buildString {
        if (days > 0) append("${days.toString().padStart(2, '0')} Days ")
        if (hours > 0) append("${hours.toString().padStart(2, '0')} hr ")
        if (minutes > 0) append("${minutes.toString().padStart(2, '0')} min ")
        if (seconds > 0) append("${seconds.toString().padStart(2, '0')} sec")
    }
}

fun Int.convertToHMS(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    return buildString {
        if (hours > 0) append("${hours.toString().padStart(2, '0')} hr ")
        if (minutes > 0) append("${minutes.toString().padStart(2, '0')} min ")
        if (seconds > 0) append("${seconds.toString().padStart(2, '0')} sec")
    }
}

fun Long.convertToDHMS(): String {
    val days = this / (3600 * 24)
    val hours = (this % (3600 * 24)) / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    return buildString {
        if (days > 0) append("${days.toString().padStart(2, '0')} Days ")
        if (hours > 0) append("${hours.toString().padStart(2, '0')} hr ")
        if (minutes > 0) append("${minutes.toString().padStart(2, '0')} min ")
        if (seconds > 0) append("${seconds.toString().padStart(2, '0')} sec")
    }
}

fun Long.convertToHMS(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    return buildString {
        if (hours > 0) append("${hours.toString().padStart(2, '0')} hr ")
        if (minutes > 0) append("${minutes.toString().padStart(2, '0')} min ")
        if (seconds > 0) append("${seconds.toString().padStart(2, '0')} sec")
    }
}

fun Int.convertTimeStampToDateString(format: String = DefaultAppDateFormat): String {
    val instant = Instant.fromEpochSeconds(this.toLong())
    val localDateTime = instant.toLocalDateTime(systemTimeZone)
    return dateTimeFormatter(format).format(localDateTime)
}

private val systemTimeZone: TimeZone = TimeZone.currentSystemDefault()

@OptIn(FormatStringsInDatetimeFormats::class)
private fun dateTimeFormatter(pattern: String): DateTimeFormat<LocalDateTime> =
        formatterCache.getOrPut(pattern) {
            if (pattern == DefaultAppDateFormat) {
                LocalDateTime.Format {
                    dayOfWeek(DayOfWeekNames.ENGLISH_ABBREVIATED)
                    char(' ')
                    monthName(MonthNames.ENGLISH_ABBREVIATED)
                    char(' ')
                    dayOfMonth(Padding.ZERO)
                    chars(", ")
                    year()
                    char(' ')
                    amPmHour(Padding.ZERO)
                    char(':')
                    minute(Padding.ZERO)
                    char(':')
                    second(Padding.ZERO)
                    char(' ')
                    amPmMarker(am = "AM", pm = "PM")
                }
            } else {
                LocalDateTime.Format { byUnicodePattern(pattern) }
            }
        }

private val formatterCache: MutableMap<String, DateTimeFormat<LocalDateTime>> = mutableMapOf()
