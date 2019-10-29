package com.codeforcesvisualizer.util

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.codeforcesvisualizer.R
import com.github.mikephil.charting.utils.ColorTemplate
import com.wefika.flowlayout.FlowLayout
import java.util.*
import kotlin.math.roundToInt

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun convertDpToPixel(dp: Int): Int {
    return (dp * (Resources.getSystem().displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT)).roundToInt()
}

fun setUpChipsLayout(view: View): View {

    val marginLayoutParams = FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    marginLayoutParams.setMargins(0, 0, convertDpToPixel(5), convertDpToPixel(5))
    view.layoutParams = marginLayoutParams
    view.setPadding(8, 8, 8, 8)
    val drawable = view.background as GradientDrawable

    val color = getRandomColor()

    drawable.setColor(color)

    if (view is TextView) {
        if (isBrightColor(color)) {
            view.setTextColor(Color.BLACK)
        } else {
            view.setTextColor(Color.WHITE)
        }
    }
    return view
}

fun setUpCircleChipsLayout(view: View): View {
    val marginLayoutParams = FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    marginLayoutParams.setMargins(0, 0, convertDpToPixel(5), convertDpToPixel(5))
    view.layoutParams = marginLayoutParams
    view.setPadding(8, 8, 8, 8)
    view.setBackgroundResource(R.drawable.circle_border)

    if (view is TextView) {
        view.setTextColor(Color.WHITE)
    }

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

fun isBrightColor(color: Int): Boolean {
    if (Color.TRANSPARENT == color)
        return true

    var rtnValue = false

    val rgb = intArrayOf(Color.red(color), Color.green(color), Color.blue(color))

    val brightness = Math.sqrt(rgb[0].toDouble() * rgb[0].toDouble() * .241 + (rgb[1].toDouble()
            * rgb[1].toDouble() * .691) + rgb[2].toDouble() * rgb[2].toDouble() * .068).toInt()

    // color is light
    if (brightness >= 200) {
        rtnValue = true
    }

    return rtnValue
}