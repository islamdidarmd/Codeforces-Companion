package com.codeforcesvisualizer.contest.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.codeforcesvisualizer.contest.R
import com.codeforcesvisualizer.domain.entity.Contest

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ContestList(
    modifier: Modifier = Modifier,
    contestList: List<Contest>,
    openContestDetails: (Int) -> Unit,
    onOpenWebSite: (Int) -> Unit,
) {
    val state = rememberLazyListState()

    val upcoming = contestList.filter { it.scheduled }.asReversed()
    val past = contestList.filter { !it.scheduled }
    val grouped = linkedMapOf<String, List<Contest>>()

    if (upcoming.isNotEmpty()) grouped[stringResource(id = R.string.upcoming)] = upcoming
    grouped[stringResource(id = R.string.past)] = past

    LazyColumn(modifier = modifier, state = state) {
        grouped.forEach { (title, list) ->
            stickyHeader {
                Header(modifier = Modifier, text = title)
            }
            items(list) { contest ->
                ContestListItem(
                    modifier = Modifier,
                    contest = contest,
                    openContestDetails = openContestDetails,
                    onOpenWebSite = onOpenWebSite
                )
            }
        }
    }
}