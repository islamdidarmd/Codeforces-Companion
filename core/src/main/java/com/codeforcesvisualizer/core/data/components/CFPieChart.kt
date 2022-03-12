package com.codeforcesvisualizer.core.data.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.ArrayList

@SuppressLint("ModifierParameter")
@Composable
fun CFPieChart(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(320.dp),
    entries: List<PieEntry>,
    itemCount: Int,
    minSizePercentToDrawLabel: Int
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            return@AndroidView PieChart(context).apply {
                val dataset = PieDataSet(entries, "").apply {
                    colors = getColorList()
                    valueTextSize = 14f
                    setValueFormatter { value, entry, _, _ ->
                        return@setValueFormatter if (((value / itemCount) * 100) < minSizePercentToDrawLabel) {
                            (entry as PieEntry).label = ""
                            ""
                        } else value.toInt().toString()
                    }
                }
                val data = PieData(dataset)
                description.isEnabled = false
                isDrawHoleEnabled = false
                legend.apply {
                    verticalAlignment = Legend.LegendVerticalAlignment.TOP
                    orientation = Legend.LegendOrientation.VERTICAL
                    xEntrySpace = 7f
                    yEntrySpace = 5f
                }
                setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                    override fun onValueSelected(e: Entry?, h: Highlight?) {
                        val entry = e as PieEntry
                        val text = "${entry.value.toInt()})"
                        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                    }

                    override fun onNothingSelected() {

                    }
                })
                setData(data)
                animateY(2000)
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