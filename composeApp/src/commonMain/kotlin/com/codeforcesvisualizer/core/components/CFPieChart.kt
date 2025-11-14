package com.codeforcesvisualizer.core.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Immutable
data class PieChartSlice(
    val label: String,
    val value: Float,
    val color: Color
)

@Immutable
data class CFPieChartData(
    val slices: List<PieChartSlice>
) {
    val total: Float
        get() = slices.sumOf { it.value.toDouble() }.toFloat()

    val hasData: Boolean
        get() = slices.isNotEmpty() && total > 0f
}

@Composable
fun CFPieChart(
    modifier: Modifier = Modifier,
    data: CFPieChartData,
    minPercentToShowLabel: Int = 10
) {
    if (!data.hasData) {
        Center(modifier = modifier.height(200.dp)) {
            Text(text = "No data available", style = MaterialTheme.typography.bodyMedium)
        }
        return
    }

    Column(modifier = modifier.fillMaxWidth()) {
        val chartHeight = 260.dp
        val textMeasurer = rememberTextMeasurer()
        val axisTextStyle =
            MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface)

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(chartHeight)
        ) {
            val diameter = min(size.width, size.height)
            val radius = diameter / 2f
            val center = Offset(size.width / 2f, size.height / 2f)
            val total = data.total
            var startAngle = -90f

            data.slices.forEach { slice ->
                val sweep = (slice.value / total) * 360f
                drawArc(
                    color = slice.color,
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = true,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = androidx.compose.ui.geometry.Size(diameter, diameter)
                )

                val percent = (slice.value / total) * 100f
                if (percent >= minPercentToShowLabel) {
                    val angleRad = (startAngle + sweep / 2f) * (PI / 180f)
                    val labelRadius = radius * 0.6f
                    val labelCenter = Offset(
                        x = center.x + (labelRadius * cos(angleRad)).toFloat(),
                        y = center.y + (labelRadius * sin(angleRad)).toFloat()
                    )
                    val label = "${slice.label} ${percent.toInt()}%"
                    val textLayout = textMeasurer.measure(
                        text = AnnotatedString(label),
                        style = axisTextStyle.copy(textAlign = TextAlign.Center)
                    )
                    drawText(
                        textLayoutResult = textLayout,
                        topLeft = Offset(
                            x = labelCenter.x - textLayout.size.width / 2f,
                            y = labelCenter.y - textLayout.size.height / 2f
                        )
                    )
                }

                startAngle += sweep
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            data.slices.forEach { slice ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(slice.color, CircleShape)
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "${slice.label} (${slice.value.toInt()})",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                    )
                }
            }
        }
    }
}

fun getPieChartColorList(): List<Color> = listOf(
    Color(0xFFEF5350),
    Color(0xFFAB47BC),
    Color(0xFF5C6BC0),
    Color(0xFF29B6F6),
    Color(0xFF26A69A),
    Color(0xFF9CCC65),
    Color(0xFFFFCA28),
    Color(0xFFFF7043)
)