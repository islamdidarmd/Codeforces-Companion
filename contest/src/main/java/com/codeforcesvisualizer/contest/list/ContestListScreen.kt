package com.codeforcesvisualizer.contest.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeforcesvisualizer.contest.R
import com.codeforcesvisualizer.core.data.components.CFAppBar
import com.codeforcesvisualizer.core.data.components.CFLoadingIndicator
import com.codeforcesvisualizer.core.data.components.Center

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
        openSearch = openSearch,
        openContestDetails = openContestDetails,
        onOpenWebSite = onOpenWebSite
    )
}

@Composable
internal fun ContestListScreen(
    modifier: Modifier = Modifier,
    state: State<ContestListUiState>,
    openSearch: () -> Unit,
    openContestDetails: (Int) -> Unit,
    onOpenWebSite: (Int) -> Unit,
) {

    Scaffold(
        modifier = modifier,
        topBar = { TopBar(openSearch = openSearch) }) { innerPadding ->
        when {
            state.value.loading -> {
                CFLoadingIndicator(modifier = modifier.padding(innerPadding))
            }
            state.value.userMessage.isNotBlank() -> {
                Center(modifier = modifier.padding(innerPadding)) {
                    Text(text = state.value.userMessage)
                }
            }
            else -> {
                ContestList(
                    modifier = Modifier.padding(innerPadding),
                    contestList = state.value.contestList,
                    openContestDetails = openContestDetails,
                    onOpenWebSite = onOpenWebSite
                )
            }
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