package com.codeforcesvisualizer.core.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeforcesvisualizer.core.theme.CFThemeColors
import com.codeforcesvisualizer.core.theme.rankColorFor

@Immutable
data class DifficultyBucket(
    val range: String,
    val count: Int,
)

@Composable
fun DifficultyHistogram(
    buckets: List<DifficultyBucket>,
    modifier: Modifier = Modifier,
) {
    val colors = CFThemeColors.current
    val textMeasurer = rememberTextMeasurer()

    val maxCount = remember(buckets) {
        buckets.maxOfOrNull { it.count } ?: 1
    }

    val labelStyle = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontSize = 7.5.sp,
        color = colors.dim,
        textAlign = TextAlign.Center,
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        if (buckets.isEmpty()) return@Canvas

        val bottomPadding = 20.dp.toPx()
        val topPadding = 8.dp.toPx()
        val plotHeight = size.height - bottomPadding - topPadding
        val barGap = 2.dp.toPx()
        val bucketCount = buckets.size
        val barWidth = (size.width - barGap * (bucketCount - 1)) / bucketCount

        buckets.forEachIndexed { index, bucket ->
            val ratio = bucket.count.toFloat() / maxCount.coerceAtLeast(1)
            val barHeight = ratio * plotHeight
            val x = index * (barWidth + barGap)
            val y = topPadding + plotHeight - barHeight

            // Parse range to get rating for color
            val ratingValue = bucket.range.filter { it.isDigit() }.toIntOrNull() ?: 0
            val barColor = rankColorFor(ratingValue).copy(alpha = 0.85f)

            drawRect(
                color = barColor,
                topLeft = Offset(x, y),
                size = Size(barWidth, barHeight),
            )

            // X-axis label below each bar
            val labelMeasured = textMeasurer.measure(
                AnnotatedString(bucket.range),
                style = labelStyle,
            )
            drawText(
                textLayoutResult = labelMeasured,
                topLeft = Offset(
                    x = x + (barWidth - labelMeasured.size.width) / 2f,
                    y = topPadding + plotHeight + 4.dp.toPx(),
                ),
            )
        }
    }
}
