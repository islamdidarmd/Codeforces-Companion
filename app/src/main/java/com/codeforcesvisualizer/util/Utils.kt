package com.codeforcesvisualizer.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import com.codeforcesvisualizer.R
import com.codeforcesvisualizer.model.Problem
import com.codeforcesvisualizer.model.UserStatus
import com.wefika.flowlayout.FlowLayout
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import android.graphics.drawable.GradientDrawable
import com.github.mikephil.charting.utils.ColorTemplate


fun show(view: View) {
    view.visibility = View.VISIBLE
}

fun hide(view: View) {
    view.visibility = View.GONE
}

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

fun convertDpToPixel(dp: Int): Int {
    return Math.round(dp * (Resources.getSystem().displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

fun setUpChipsLayout(view: View): View {

    val marginLayoutParams = FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    marginLayoutParams.setMargins(0, 0, convertDpToPixel(5), convertDpToPixel(5))
    view.layoutParams = marginLayoutParams
    view.setPadding(8, 8, 8, 8)
    val drawable = view.background as GradientDrawable

    drawable.setColor(getRandomColor())

    return view
}

fun getRandomColor(): Int {
    val rnd = Random()
    return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
}

fun getColorList(): List<Int> {
    val colorList: MutableList<Int> = ArrayList()
    colorList.addAll(ColorTemplate.COLORFUL_COLORS.toList())
    colorList.addAll(ColorTemplate.MATERIAL_COLORS.toList())
    colorList.addAll(ColorTemplate.PASTEL_COLORS.toList())
    colorList.addAll(ColorTemplate.LIBERTY_COLORS.toList())
    return colorList
}