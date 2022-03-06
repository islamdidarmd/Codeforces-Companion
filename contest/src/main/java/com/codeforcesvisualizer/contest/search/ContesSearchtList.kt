package com.codeforcesvisualizer.contest.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.codeforcesvisualizer.contest.R
import com.codeforcesvisualizer.contest.list.ContestListItem
import com.codeforcesvisualizer.contest.list.Header
import com.codeforcesvisualizer.domain.entity.Contest

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ContestSearchList(
    modifier: Modifier = Modifier,
    contestList: List<Contest>
) {
    LazyColumn(modifier = modifier) {
        if (contestList.isNotEmpty()) stickyHeader {
            Header(text = "Results")
        }
        items(contestList) { contest ->
            ContestListItem(modifier = Modifier, contest = contest)
        }
    }
}