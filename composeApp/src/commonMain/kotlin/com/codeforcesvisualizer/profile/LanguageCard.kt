package com.codeforcesvisualizer.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.codeforcesvisualizer.core.components.CFCard
import com.codeforcesvisualizer.core.components.LanguageBarChart
import com.codeforcesvisualizer.core.components.LanguageData
import com.codeforcesvisualizer.shared.domain.entity.UserStatus

@Composable
fun LanguageCard(
    userStatusList: List<UserStatus>,
    modifier: Modifier = Modifier
) {
    val languages = remember(userStatusList) {
        val countMap = mutableMapOf<String, Int>()
        userStatusList.forEach { status ->
            countMap[status.programmingLanguage] =
                (countMap[status.programmingLanguage] ?: 0) + 1
        }
        val total = countMap.values.sum().toFloat().coerceAtLeast(1f)
        countMap.entries
            .sortedByDescending { it.value }
            .map { (name, count) ->
                LanguageData(
                    name = name,
                    percentage = (count / total) * 100f
                )
            }
    }

    CFCard(
        title = "languages",
        modifier = modifier
    ) {
        LanguageBarChart(languages = languages)
    }
}
