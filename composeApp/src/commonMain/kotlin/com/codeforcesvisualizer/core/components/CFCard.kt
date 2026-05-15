package com.codeforcesvisualizer.core.components

import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeforcesvisualizer.core.theme.CFThemeColors

@Composable
fun CFCard(
    modifier: Modifier = Modifier,
    title: String? = null,
    titleRight: @Composable (() -> Unit)? = null,
    noBorder: Boolean = false,
    contentPadding: Dp = 14.dp,
    content: @Composable () -> Unit,
) {
    val colors = CFThemeColors.current
    val shape = RoundedCornerShape(12.dp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(colors.surface)
            .then(
                if (noBorder) Modifier
                else Modifier.border(width = 1.dp, color = colors.border, shape = shape)
            ),
    ) {
        if (title != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp, end = 14.dp, top = 14.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = colors.violet)) {
                            append("// ")
                        }
                        withStyle(SpanStyle(color = colors.dim)) {
                            append(title.uppercase())
                        }
                    },
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        letterSpacing = 0.08.sp,
                    ),
                    modifier = Modifier.weight(1f),
                )
                if (titleRight != null) {
                    titleRight()
                }
            }
            HeightSpacer(height = 10.dp)
        }

        Box(modifier = Modifier.padding(contentPadding)) {
            content()
        }
    }
}
