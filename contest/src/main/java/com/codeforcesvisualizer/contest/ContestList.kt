package com.codeforcesvisualizer.contest

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.codeforcesvisualizer.core.data.ui.Chip
import com.codeforcesvisualizer.domain.entity.Contest
import com.codeforcesvisualizer.domain.entity.Phase

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ContestList(
    modifier: Modifier = Modifier,
    contestList: List<Contest>
) {
    val upcoming = contestList.filter { it.phase == Phase.BEFORE }
    val past = contestList.filter { it.phase != Phase.BEFORE }
    val grouped = linkedMapOf<String, List<Contest>>()

    if (upcoming.isNotEmpty()) grouped["Upcoming"] = upcoming
    grouped["Past"] = past

    LazyColumn(modifier = modifier) {
        grouped.forEach { (title, list) ->
            stickyHeader {
                Header(modifier = Modifier, text = title)
            }
            items(list) { contest ->
                ContestListItem(modifier = Modifier, contest = contest)
            }
        }
    }
}

@Composable
internal fun Header(modifier: Modifier = Modifier, text: String) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.LightGray)
    ) {
        Text(text = text, modifier = Modifier.padding(8.dp))
    }
}

@Composable
internal fun ContestListItem(modifier: Modifier = Modifier, contest: Contest) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = contest.name)
            Chip(label = contest.type.name) {}
        }
    }
}