package com.codeforcesvisualizer.preference

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codeforcesvisualizer.core.components.HeightSpacer
import com.codeforcesvisualizer.core.components.WidthSpacer
import com.codeforcesvisualizer.core.theme.CFThemeColors
import com.codeforcesvisualizer.core.theme.DarkBg
import com.codeforcesvisualizer.core.theme.DarkBorder
import com.codeforcesvisualizer.core.theme.DarkSurface
import com.codeforcesvisualizer.core.theme.LightBg
import com.codeforcesvisualizer.core.theme.LightBorder
import com.codeforcesvisualizer.core.theme.LightSurface
import com.codeforcesvisualizer.core.theme.Violet
import com.codeforcesvisualizer.shared.domain.entity.UiThemeMode

@Composable
fun AppearanceSection(
    modifier: Modifier = Modifier,
    themeMode: UiThemeMode,
    onThemeModeChanged: (UiThemeMode) -> Unit
) {
    val colors = CFThemeColors.current

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ThemePreviewButton(
            label = "dark",
            isSelected = themeMode == UiThemeMode.Dark,
            previewBg = DarkBg,
            previewSurface = DarkSurface,
            previewBorder = DarkBorder,
            onClick = { onThemeModeChanged(UiThemeMode.Dark) },
            modifier = Modifier.weight(1f),
        )
        ThemePreviewButton(
            label = "light",
            isSelected = themeMode == UiThemeMode.Light,
            previewBg = LightBg,
            previewSurface = LightSurface,
            previewBorder = LightBorder,
            onClick = { onThemeModeChanged(UiThemeMode.Light) },
            modifier = Modifier.weight(1f),
        )
        ThemePreviewButton(
            label = "system",
            isSelected = themeMode == UiThemeMode.System,
            previewBg = DarkBg,
            previewSurface = LightSurface,
            previewBorder = DarkBorder,
            onClick = { onThemeModeChanged(UiThemeMode.System) },
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun ThemePreviewButton(
    label: String,
    isSelected: Boolean,
    previewBg: Color,
    previewSurface: Color,
    previewBorder: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = CFThemeColors.current
    val borderColor = if (isSelected) colors.violet else colors.border
    val shape = RoundedCornerShape(10.dp)

    Column(
        modifier = modifier
            .clip(shape)
            .background(colors.surface)
            .border(width = if (isSelected) 2.dp else 1.dp, color = borderColor, shape = shape)
            .clickable { onClick() }
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Mini preview card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(previewBg)
                .border(1.dp, previewBorder, RoundedCornerShape(6.dp))
                .padding(8.dp),
        ) {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Violet)
                )
                HeightSpacer(height = 4.dp)
                Box(
                    modifier = Modifier
                        .width(28.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(previewSurface)
                )
                HeightSpacer(height = 4.dp)
                Box(
                    modifier = Modifier
                        .width(34.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(previewSurface)
                )
            }
        }

        HeightSpacer(height = 8.dp)

        Text(
            text = label,
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) colors.violet else colors.dim,
            ),
        )
    }
}
