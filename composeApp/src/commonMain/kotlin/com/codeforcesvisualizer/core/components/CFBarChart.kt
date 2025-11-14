package com.codeforcesvisualizer.core.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Immutable
data class BarChartSeries(
    val label: String,
    val values: List<Float>,
    val color: Color
)

@Immutable
data class CFBarChartData(
    val groupLabels: List<String>,
    val series: List<BarChartSeries>
) {
    val maxValue: Float
        get() = series.flatMap { it.values }.maxOrNull() ?: 0f

    val hasData: Boolean
        get() = groupLabels.isNotEmpty() && series.isNotEmpty()
}

@Composable
fun CFBarChart(
    modifier: Modifier = Modifier,
    data: CFBarChartData,
    showLegend: Boolean = false,
    yAxisSteps: Int = 4,
    yAxisLabelFormatter: (Float) -> String = { it.toInt().toString() }
) {
    if (!data.hasData) {
        Center(modifier = modifier.height(200.dp)) {
            Text(text = "No data available", style = MaterialTheme.typography.bodyMedium)
        }
        return
    }

    Column(modifier = modifier.fillMaxWidth()) {
        val chartHeight = 240.dp
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(chartHeight)
        ) {
            val textMeasurer = rememberTextMeasurer()
            val yGuides = remember(yAxisSteps, data.maxValue) {
                buildList {
                    val maxValue = data.maxValue.takeIf { it > 0f } ?: 1f
                    val steps = yAxisSteps.coerceAtLeast(1)
                    val stepValue = maxValue / steps
                    for (i in 0..steps) {
                        add(i * stepValue)
                    }
                }
            }
            val axisTextStyle = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp)
            val axisTextColor = MaterialTheme.colorScheme.onSurfaceVariant
            val leftPadding = 36.dp
            val bottomPadding = 32.dp
            val colorScheme = MaterialTheme.colorScheme

            Canvas(modifier = Modifier.matchParentSize()) {
                val leftPaddingPx = leftPadding.toPx()
                val bottomPaddingPx = bottomPadding.toPx()
                val plotWidth = size.width - leftPaddingPx
                val plotHeight = size.height - bottomPaddingPx
                val groupCount = data.groupLabels.size
                val seriesCount = data.series.size
                val groupWidth = if (groupCount == 0) 0f else plotWidth / groupCount
                val barWidth = if (seriesCount == 0) 0f else groupWidth / (seriesCount + 1f)
                val maxValue = data.maxValue.takeIf { it > 0f } ?: 1f

                yGuides.forEach { value ->
                    val ratio = value / maxValue
                    val y = plotHeight - (ratio * plotHeight)
                    drawLine(
                        color = colorScheme.outline.copy(alpha = 0.2f),
                        start = Offset(leftPaddingPx, y),
                        end = Offset(size.width, y),
                        strokeWidth = 1.dp.toPx()
                    )

                    val label = yAxisLabelFormatter(value)
                    val textLayout = textMeasurer.measure(
                        text = AnnotatedString(label),
                        style = axisTextStyle.copy(color = axisTextColor)
                    )

                    drawYAxisLabel(textLayout, leftPaddingPx, y)
                }

                data.groupLabels.indices.forEach { groupIndex ->
                    val groupStart = leftPaddingPx + groupWidth * groupIndex + (groupWidth - seriesCount * barWidth) / 2f
                    val groupCenter = groupStart + (seriesCount * barWidth) / 2f

                    // X axis label
                    val label = data.groupLabels[groupIndex]
                    val labelLayout = textMeasurer.measure(
                        text = AnnotatedString(label),
                        style = axisTextStyle.copy(color = axisTextColor, textAlign = TextAlign.Center)
                    )
                    drawText(
                        textLayoutResult = labelLayout,
                        topLeft = Offset(
                            x = groupCenter - labelLayout.size.width / 2f,
                            y = plotHeight + 8.dp.toPx()
                        )
                    )

                    data.series.forEachIndexed { seriesIndex, series ->
                        val value = series.values.getOrNull(groupIndex) ?: 0f
                        val ratio = value / maxValue
                        val barHeight = ratio * plotHeight
                        val left = groupStart + seriesIndex * barWidth
                        val top = plotHeight - barHeight
                        drawRect(
                            color = series.color,
                            topLeft = Offset(left, top),
                            size = Size(barWidth, barHeight)
                        )
                    }
                }

                drawLine(
                    color = colorScheme.outline,
                    start = Offset(leftPaddingPx, plotHeight),
                    end = Offset(size.width, plotHeight),
                    strokeWidth = 1.dp.toPx()
                )
            }
        }

        if (showLegend) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                data.series.forEach { series ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        androidx.compose.foundation.layout.Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(series.color, CircleShape)
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            text = series.label,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                        )
                    }
                }
            }
        }
    }
}

fun getBarChartColorList(): List<Color> = listOf(
    Color(0xFF4285F4),
    Color(0xFFDB4437),
    Color(0xFFF4B400),
    Color(0xFF0F9D58),
    Color(0xFFAB47BC),
    Color(0xFF00ACC1),
    Color(0xFFEF6C00),
    Color(0xFF7CB342)
)

@OptIn(ExperimentalTextApi::class)
private fun DrawScope.drawYAxisLabel(
    textLayout: TextLayoutResult,
    leftPadding: Float,
    y: Float
) {
    drawText(
        textLayoutResult = textLayout,
        topLeft = Offset(
            x = leftPadding - textLayout.size.width - 8.dp.toPx(),
            y = y - textLayout.size.height / 2f
        )
    )
}