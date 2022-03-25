package com.codeforcesvisualizer.contest.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeforcesvisualizer.contest.R
import com.codeforcesvisualizer.core.components.CFAppBar
import com.codeforcesvisualizer.core.components.CFLoadingIndicator
import com.codeforcesvisualizer.core.components.Center
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ContestListScreen(
    modifier: Modifier = Modifier,
    viewModel: ContestViewModel = hiltViewModel(),
    openSearch: () -> Unit,
    openContestDetails: (Int) -> Unit,
    onOpenWebSite: (Int) -> Unit,
) {
    val uiState = viewModel.uiState.collectAsState()
    ContestListScreen(
        modifier = modifier,
        state = uiState,
        onRefresh = { viewModel.refreshContestList() },
        openSearch = openSearch,
        openContestDetails = openContestDetails,
        onOpenWebSite = onOpenWebSite
    )
}

@Composable
private fun ContestListScreen(
    modifier: Modifier = Modifier,
    state: State<ContestListUiState>,
    onRefresh: () -> Unit,
    openSearch: () -> Unit,
    openContestDetails: (Int) -> Unit,
    onOpenWebSite: (Int) -> Unit,
) {
    val isRefreshing = rememberSwipeRefreshState(isRefreshing = state.value.refreshing)

    Scaffold(
        modifier = modifier,
        topBar = { TopBar(openSearch = openSearch) },
        floatingActionButton = {
            if (state.value.userMessage.isNotEmpty()) FloatingActionButton(
                onClick = onRefresh
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
            }
        }
    )
    { innerPadding ->
        SwipeRefresh(
            state = isRefreshing,
            onRefresh = onRefresh
        ) {
            ContestListScreen(
                modifier = modifier.padding(innerPadding),
                state = state,
                openContestDetails = openContestDetails,
                onOpenWebSite = onOpenWebSite
            )
        }
    }
}

@Composable
private fun ContestListScreen(
    modifier: Modifier = Modifier,
    state: State<ContestListUiState>,
    openContestDetails: (Int) -> Unit,
    onOpenWebSite: (Int) -> Unit,
) {
    when {
        state.value.loading -> {
            CFLoadingIndicator(modifier = modifier)
        }
        state.value.userMessage.isNotBlank() -> {
            Center(modifier = modifier) {
                Text(text = state.value.userMessage)
            }
        }
        else -> {
            ContestList(
                modifier = modifier,
                contestList = state.value.contestList,
                openContestDetails = openContestDetails,
                onOpenWebSite = onOpenWebSite
            )
        }
    }
}

@Composable
private fun TopBar(openSearch: () -> Unit) {
    CFAppBar(
        title = stringResource(id = R.string.contests),
        actions = {
            IconButton(onClick = openSearch) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
    )
}

@Preview
@Composable
private fun Preview() {
    ContestListScreen(openSearch = {}, openContestDetails = {}, onOpenWebSite = {})
}