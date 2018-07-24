package com.codeforcesvisualizer.util

import android.content.Context
import android.view.View
import com.codeforcesvisualizer.model.UserStatus
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun show(view: View) {
    view.visibility = View.VISIBLE
}

fun hide(view: View) {
    view.visibility = View.GONE
}

fun secToHourMins(totalSecs: Long): String {
    val hours = totalSecs / 3600;
    val minutes = (totalSecs % 3600) / 60;
    val seconds = totalSecs % 60;
    return String.format("%02d hr %02d min %02d sec", hours, minutes, seconds)
}

/**
 * Converts Unix timestamp to date
 *
 * @param timestamp Unix timestamp
 * @param format    desired date format
 * @return converted date
 */
fun getDateFromTimeStamp(timestamp: Long, format: String): String {
    val stamp = Timestamp(timestamp * 1000)
    val date = Date(stamp.time)
    return SimpleDateFormat(format).format(date)
}

fun minifyVerdicts(status: UserStatus): UserStatus {
    when (status.verdict) {
        "OK" -> {
            status.verdict = "AC"
        }

        "COMPILATION_ERROR" -> {
            status.verdict = "CE"
        }

        "RUNTIME_ERROR" -> {
            status.verdict = "RTE"
        }

        "WRONG_ANSWER" -> {
            status.verdict = "WA"
        }

        "PRESENTATION_ERROR" -> {
            status.verdict = "PE"
        }

        "TIME_LIMIT_EXCEEDED" -> {
            status.verdict = "TLE"
        }

        "MEMORY_LIMIT_EXCEEDED" -> {
            status.verdict = "MLE"
        }

        "IDLENESS_LIMIT_EXCEEDED" -> {
            status.verdict = "ILE"
        }

        "SECURITY_VIOLATED" -> {
            status.verdict = "SV"
        }

        "INPUT_PREPARATION_CRASHED" -> {
            status.verdict = "IPC"
        }

    }
    return status
}