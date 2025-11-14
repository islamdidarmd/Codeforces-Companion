package com.codeforcesvisualizer.core.utils

import com.codeforcesvisualizer.core.DefaultAppDateFormat
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

fun Int.convertToDHMS(): String {
    val days = this / (3600 * 24)
    val hours = (this % (3600 * 24)) / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    return buildString {
        if(days > 0) append(String.format("%02d Days ", days))
        if (hours > 0) append(String.format("%02d hr ", hours))
        if (minutes > 0) append(String.format("%02d min ", minutes))
        if (seconds > 0) append(String.format("%02d sec", seconds))
    }
}
fun Int.convertToHMS(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    return buildString {
        if (hours > 0) append(String.format("%02d hr ", hours))
        if (minutes > 0) append(String.format("%02d min ", minutes))
        if (seconds > 0) append(String.format("%02d sec", seconds))
    }
}

fun Long.convertToDHMS(): String {
    val days = this / (3600 * 24)
    val hours = (this % (3600 * 24)) / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    return buildString {
        if(days > 0) append(String.format("%02d Days ", days))
        if (hours > 0) append(String.format("%02d hr ", hours))
        if (minutes > 0) append(String.format("%02d min ", minutes))
        if (seconds > 0) append(String.format("%02d sec", seconds))
    }
}

fun Long.convertToHMS(): String {
    val hours = this / 3600
    val minutes = (this % 3600) / 60
    val seconds = this % 60
    return buildString {
        if (hours > 0) append(String.format("%02d hr ", hours))
        if (minutes > 0) append(String.format("%02d min ", minutes))
        if (seconds > 0) append(String.format("%02d sec", seconds))
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
        LocalDateTime.Format {
            byUnicodePattern(pattern)
        }
    }

private val formatterCache: MutableMap<String, DateTimeFormat<LocalDateTime>> = mutableMapOf()