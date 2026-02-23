package com.codeforcesvisualizer.contest.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import codeforces_visualizer.composeapp.generated.resources.Res
import codeforces_visualizer.composeapp.generated.resources.contests
import codeforces_visualizer.composeapp.generated.resources.refresh
import codeforces_visualizer.composeapp.generated.resources.search
import com.codeforcesvisualizer.core.EventLogger
import com.codeforcesvisualizer.core.components.CFAppBar
import com.codeforcesvisualizer.core.components.CFLoadingIndicator
import com.codeforcesvisualizer.core.components.Center
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ContestListScreen(
    modifier: Modifier = Modifier,
    viewModel: ContestViewModel = koinViewModel(),
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

    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(openSearch = {
                openSearch()
                EventLogger.logScreenView("Contest Search")
            })
        },
        floatingActionButton = {
            if (state.value.userMessage.isNotEmpty()) FloatingActionButton(
                onClick = {
                    onRefresh()
                    EventLogger.logEvent("Refresh Contest List")
                }
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = stringResource(Res.string.refresh))
            }
        }
    )
    { innerPadding ->
        ContestListScreen(
            modifier = modifier.padding(innerPadding),
            state = state,
            openContestDetails = openContestDetails,
            onOpenWebSite = onOpenWebSite
        )
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
        title = stringResource(resource = Res.string.contests),
        actions = {
            IconButton(onClick = openSearch) {
                Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(Res.string.search))
            }
        }
    )
}

@Preview
@Composable
private fun Preview() {
    ContestListScreen(openSearch = {}, openContestDetails = {}, onOpenWebSite = {})
}