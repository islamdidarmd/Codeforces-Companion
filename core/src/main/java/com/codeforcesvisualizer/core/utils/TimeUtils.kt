package com.codeforcesvisualizer.core.utils

import com.codeforcesvisualizer.core.DefaultAppDateFormat
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

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
    val stamp = Timestamp((this * 1000L))
    val date = Date(stamp.time)
    return SimpleDateFormat(format).format(date)
}