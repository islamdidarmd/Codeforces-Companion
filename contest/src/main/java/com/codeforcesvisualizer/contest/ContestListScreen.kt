package com.codeforcesvisualizer.contest

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeforcesvisualizer.core.data.ui.CFLoadingIndicator

@Composable
fun ContestListScreen(
    modifier: Modifier,
    viewModel: ContestViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.collectAsState(initial = ContestListUiState())
    viewModel.loadContestList()
    ContestListScreen(modifier = modifier, state = uiState)
}

@Composable
internal fun ContestListScreen(
    modifier: Modifier,
    state: State<ContestListUiState>,
) {
    if (state.value.loading) {
        CFLoadingIndicator(modifier = modifier)
    } else {
        ContestList(modifier = modifier, contestList = state.value.contestList)
    }
}