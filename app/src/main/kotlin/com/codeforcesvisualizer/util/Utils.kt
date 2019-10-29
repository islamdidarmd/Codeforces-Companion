package com.codeforcesvisualizer.util

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.codeforcesvisualizer.R
import com.codeforcesvisualizer.model.Contest
import com.codeforcesvisualizer.model.Problem
import com.codeforcesvisualizer.model.UserStatus
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.wefika.flowlayout.FlowLayout
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


fun secToHourMins(totalSecs: Long): String {
    val hours = totalSecs / 3600
    val minutes = (totalSecs % 3600) / 60
    val seconds = totalSecs % 60
    return String.format("%02d hr %02d min", hours, minutes)
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

fun isValidProblem(problem: Problem): Boolean {
    return (problem.index == "A"
            || problem.index == "B"
            || problem.index == "C"
            || problem.index == "D"
            || problem.index == "E"
            || problem.index == "F"
            || problem.index == "G"
            || problem.index == "H"
            || problem.index == "I"
            || problem.index == "J"
            || problem.index == "K"
            || problem.index == "L"
            || problem.index == "M"
            || problem.index == "O"
            || problem.index == "Q"
            || problem.index == "R")
}

fun getIndexOfChartLabel(value: Float): Int {
    return if (value >= 1 && value < 2) {
        0
    } else if (value >= 2 && value < 3) {
        1
    } else if (value >= 3 && value < 4) {
        2
    } else if (value >= 4 && value < 5) {
        3
    } else if (value >= 5 && value < 6) {
        4
    } else if (value >= 6 && value < 7) {
        5
    } else if (value >= 7 && value < 8) {
        6
    } else if (value >= 8 && value < 9) {
        7
    } else if (value >= 9 && value < 10) {
        8
    } else if (value >= 10 && value < 11) {
        9
    } else if (value >= 11 && value < 12) {
        10
    } else if (value >= 12 && value < 13) {
        11
    } else if (value >= 13 && value < 14) {
        12
    } else if (value >= 14 && value < 15) {
        13
    } else if (value >= 15 && value < 16) {
        14
    } else if (value >= 16 && value < 17) {
        15
    } else {
        0
    }
}

fun getSolvedCount(map: MutableMap<String, Boolean>): Int {
    var count = 0
    map.values.forEach {
        if (it) count++
    }
    return count
}

fun createContestUrl(contest: Contest): String {
    return "http://codeforces.com/contest/${contest.id}"
}

fun getSolvedWithOneSubCount(map: MutableMap<String, Int>): Int {
    var count = 0

    map.values.forEach {
        if (it == 1) count++
    }

    return count
}

fun getProblemLinkFromProblem(problemId: String): String {
    val segments = problemId.split('-')

    return "http://codeforces.com/contest/${segments[0]}/problem/${segments[1]}"
}

fun Any.toJson(): String {
    return Gson().toJson(this)
}

inline fun <reified T> fromJson(src: String?): T? {
    if (src.isEmpty()) return null

    return Gson().fromJson(src, T::class.java)
}

fun Any?.isEmpty(): Boolean {
    if (this == null) return true
    if (this is String) {
        return this.isNullOrEmpty()
    }
    if (this is CharSequence) {
        return this.isNullOrEmpty()
    }
    return false
}