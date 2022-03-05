package com.codeforcesvisualizer.contest.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeforcesvisualizer.contest.R
import com.codeforcesvisualizer.core.data.components.CFLoadingIndicator
import com.codeforcesvisualizer.core.data.components.Center

@Composable
fun ContestListScreen(
    modifier: Modifier = Modifier,
    viewModel: ContestViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState(initial = ContestListUiState())
    viewModel.loadContestList()
    ContestListScreen(modifier = modifier, state = uiState)
}

@Composable
internal fun ContestListScreen(
    modifier: Modifier = Modifier,
    state: State<ContestListUiState>,
) {

    Scaffold(
        modifier = modifier,
        topBar = { TopBar() }) { innerPadding ->
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
                    contestList = state.value.contestList
                )
            }
        }
    }
}

@Composable
internal fun TopBar() {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.contests))
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
    )
}

@Preview
@Composable
private fun Preview() {
    ContestListScreen()
}