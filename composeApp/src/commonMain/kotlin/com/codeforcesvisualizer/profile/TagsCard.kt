package com.codeforcesvisualizer.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.codeforcesvisualizer.core.components.CFCard
import com.codeforcesvisualizer.core.components.TagBarsChart
import com.codeforcesvisualizer.core.components.TagData
import com.codeforcesvisualizer.core.theme.CFThemeColors
import com.codeforcesvisualizer.shared.domain.entity.UserStatus

@Composable
fun TagsCard(
    modifier: Modifier = Modifier,
    userStatusList: List<UserStatus>
) {
    val colors = CFThemeColors.current

    val tagCounts = remember(userStatusList) {
        val counts = mutableMapOf<String, Int>()
        userStatusList.forEach { status ->
            status.problem.tags.forEach { tag ->
                counts[tag] = (counts[tag] ?: 0) + 1
            }
        }
        counts.entries
            .sortedByDescending { it.value }
            .map { TagData(tag = it.key, count = it.value) }
    }

    if (tagCounts.isEmpty()) return

    CFCard(
        modifier = modifier.fillMaxWidth(),
        title = "top tags",
        titleRight = {
            androidx.compose.material3.Text(
                text = "${tagCounts.size} categories",
                style = androidx.compose.ui.text.TextStyle(
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                    fontSize = androidx.compose.ui.unit.TextUnit(10f, androidx.compose.ui.unit.TextUnitType.Sp),
                    color = colors.dim,
                ),
            )
        },
    ) {
        TagBarsChart(tags = tagCounts, maxItems = 8)
    }
}
