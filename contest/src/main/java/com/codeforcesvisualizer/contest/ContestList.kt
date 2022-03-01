package com.codeforcesvisualizer.contest

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codeforcesvisualizer.domain.entity.Contest

@Composable
internal fun ContestList(
    modifier: Modifier,
    contestList: List<Contest>
) {
    LazyColumn(modifier = modifier) {
        items(contestList) { contest ->
            Card {
                Text(text = contest.name)
            }
        }
    }
}