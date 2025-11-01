package com.codeforcesvisualizer.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

@Composable
fun CFBarChart(
    modifier: Modifier = Modifier,
    data: BarData,
    itemCount: Int,
    xAxisValueFormatter: IAxisValueFormatter,
    legendEnabled: Boolean = false,
    xAxisGranularityEnabled: Boolean = false,
    groupBars: Boolean = false,
    groupFromX: Float = 0f,
    groupSpace: Float = 0f,
    barSpace: Float = 0f,
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(320.dp),
        factory = { context ->
            return@AndroidView BarChart(context).apply {
                xAxis.apply {
                    labelCount = itemCount
                    valueFormatter = xAxisValueFormatter
                    setDrawGridLines(false)
                    position = XAxis.XAxisPosition.BOTTOM
                    granularity = 1f
                    isGranularityEnabled = xAxisGranularityEnabled
                    isGranularityEnabled = true
                    granularity
                }

                description.isEnabled = false
                legend.isEnabled = legendEnabled
                axisRight.isEnabled = false
                setFitBars(true)
                setPinchZoom(true)
                isDoubleTapToZoomEnabled = false
                axisLeft.axisMinimum = 0f

                setData(data)
                if(groupBars){
                    groupBars(groupFromX, groupSpace, barSpace)
                }
                invalidate()
                animateXY(2000, 2000)
            }
        }
    )
}

fun getBarChartColorList(): List<Int> {
    val colorList: MutableList<Int> = ArrayList()
    colorList.addAll(ColorTemplate.COLORFUL_COLORS.toList())
    colorList.addAll(ColorTemplate.MATERIAL_COLORS.toList())
    colorList.addAll(ColorTemplate.PASTEL_COLORS.toList())
    colorList.addAll(ColorTemplate.LIBERTY_COLORS.toList())
    return colorList
}