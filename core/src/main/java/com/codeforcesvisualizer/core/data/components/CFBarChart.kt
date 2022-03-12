package com.codeforcesvisualizer.core.data.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.ArrayList

@Composable
fun CFBarChart(
    modifier: Modifier = Modifier,
    entries: List<BarEntry>,
    itemCount: Int,
    xAxisValueFormatter: IAxisValueFormatter
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp),
        factory = { context ->
            return@AndroidView BarChart(context).apply {
                val dataset = BarDataSet(entries, "").apply {
                    colors = getColorList()
                }
                val data = BarData(dataset).apply {
                    barWidth = 0.5f
                    setValueFormatter { value, _, _, _ -> value.toInt().toString() }
                }
                xAxis.apply {
                    labelCount = itemCount
                    valueFormatter = xAxisValueFormatter
                    setDrawGridLines(false)
                    position = XAxis.XAxisPosition.BOTTOM
                    granularity = 1f
                    isGranularityEnabled = true
                }

                description.isEnabled = false
                legend.isEnabled = false
                axisRight.isEnabled = false
                setFitBars(true)
                setPinchZoom(true)
                isDoubleTapToZoomEnabled = false
                axisLeft.axisMinimum = 0f

                setData(data)
                animateXY(2000, 2000)
            }
        }
    )
}

private fun getColorList(): List<Int> {
    val colorList: MutableList<Int> = ArrayList()
    colorList.addAll(ColorTemplate.COLORFUL_COLORS.toList())
    colorList.addAll(ColorTemplate.MATERIAL_COLORS.toList())
    colorList.addAll(ColorTemplate.PASTEL_COLORS.toList())
    colorList.addAll(ColorTemplate.LIBERTY_COLORS.toList())
    return colorList
}