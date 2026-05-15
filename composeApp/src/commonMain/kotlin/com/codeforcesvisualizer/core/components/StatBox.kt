package com.codeforcesvisualizer.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeforcesvisualizer.core.theme.CFThemeColors

@Composable
fun StatBox(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    delta: String? = null,
    color: Color? = null,
) {
    val colors = CFThemeColors.current
    val valueColor = color ?: colors.fg

    Column(modifier = modifier) {
        Text(
            text = label.uppercase(),
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 9.sp,
                letterSpacing = 0.08.sp,
                color = colors.dim,
            ),
        )

        HeightSpacer(height = 2.dp)

        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = value,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = valueColor,
                ),
            )

            if (delta != null) {
                WidthSpacer(width = 4.dp)
                val deltaColor = if (delta.startsWith("+")) colors.green else colors.red
                Text(
                    text = delta,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        color = deltaColor,
                    ),
                )
            }
        }
    }
}
