package com.codeforcesvisualizer.contest.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeforcesvisualizer.contest.R
import com.codeforcesvisualizer.core.data.components.CFLoadingIndicator

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
    if (state.value.loading) {
        CFLoadingIndicator(modifier = modifier)
    } else {
        Scaffold(
            modifier = modifier,
            topBar = { TopBar() }) { innerPadding ->
            ContestList(
                modifier = Modifier.padding(innerPadding),
                contestList = state.value.contestList
            )
        }
    }
}

@Composable
internal fun TopBar() {
    TopAppBar(title = {
        Text(text = stringResource(id = R.string.contests))
    })
}