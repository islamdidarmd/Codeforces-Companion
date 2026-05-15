package com.codeforcesvisualizer.contest.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import codeforces_visualizer.composeapp.generated.resources.Res
import codeforces_visualizer.composeapp.generated.resources.results
import com.codeforcesvisualizer.contest.list.ContestListItem
import com.codeforcesvisualizer.contest.list.Header
import com.codeforcesvisualizer.shared.domain.entity.Contest
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ContestSearchList(
    modifier: Modifier = Modifier,
    contestList: List<Contest>,
    openContestDetails: (Int) -> Unit,
    onOpenWebSite: (Int) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        if (contestList.isNotEmpty()) stickyHeader {
            Header(text = stringResource(Res.string.results))
        }
        items(contestList) { contest ->
            ContestListItem(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                contest = contest,
                isUpcoming = contest.scheduled,
                onOpenContest = { contestId ->
                    if (contest.scheduled) {
                        openContestDetails(contestId)
                    } else {
                        onOpenWebSite(contestId)
                    }
                },
            )
        }
    }
}
