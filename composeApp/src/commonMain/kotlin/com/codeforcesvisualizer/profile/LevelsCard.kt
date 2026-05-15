package com.codeforcesvisualizer.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.codeforcesvisualizer.core.components.CFCard
import com.codeforcesvisualizer.core.components.DifficultyBucket
import com.codeforcesvisualizer.core.components.DifficultyHistogram
import com.codeforcesvisualizer.shared.domain.entity.UserStatus

@Composable
fun LevelsCard(
    modifier: Modifier = Modifier,
    userStatusList: List<UserStatus>
) {
    val buckets = remember(userStatusList) {
        val ratingRanges = listOf(800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000)
        val counts = mutableMapOf<Int, Int>()
        ratingRanges.forEach { counts[it] = 0 }

        userStatusList.forEach { status ->
            if (status.verdict == "OK") {
                val rating = status.problem.contestId
                val bucket = ratingRanges.lastOrNull { it <= rating } ?: ratingRanges.first()
                counts[bucket] = (counts[bucket] ?: 0) + 1
            }
        }

        ratingRanges.map { range ->
            DifficultyBucket(range = range.toString(), count = counts[range] ?: 0)
        }
    }

    CFCard(
        modifier = modifier.fillMaxWidth(),
        title = "solved by difficulty",
    ) {
        DifficultyHistogram(buckets = buckets)
    }
}
