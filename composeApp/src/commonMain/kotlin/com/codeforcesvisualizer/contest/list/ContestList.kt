package com.codeforcesvisualizer.contest.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import codeforces_visualizer.composeapp.generated.resources.Res
import codeforces_visualizer.composeapp.generated.resources.past
import codeforces_visualizer.composeapp.generated.resources.upcoming
import com.codeforcesvisualizer.shared.domain.entity.Contest
import org.jetbrains.compose.resources.stringResource

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

    if (upcoming.isNotEmpty()) grouped[stringResource(Res.string.upcoming)] = upcoming
    grouped[stringResource(Res.string.past)] = past

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