package com.codeforcesvisualizer.contest.search

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeforcesvisualizer.contest.R
import com.codeforcesvisualizer.contest.list.ContestViewModel
import com.codeforcesvisualizer.core.data.components.CFLoadingIndicator
import com.codeforcesvisualizer.core.data.components.Center
import com.codeforcesvisualizer.core.data.components.SearchBar

@Composable
fun ContestSearchScreen(
    modifier: Modifier = Modifier,
    contestSearchViewModel: ContestSearchViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    openContestDetails: (Int) -> Unit,
    onOpenWebSite: (Int) -> Unit,
) {
    val searchText by contestSearchViewModel.searchTextFlow.collectAsState()
    val uiState by contestSearchViewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            ContestSearchBar(
                searchText = searchText,
                onSearchTextChanged = { text -> contestSearchViewModel.onSearchTextChanged(text) },
                onNavigateBack = onNavigateBack
            )
        }
    ) { innerPadding ->
        when {
            uiState.userMessage.isNotBlank() -> {
                Center(modifier = modifier.padding(innerPadding)) {
                    Text(text = uiState.userMessage)
                }
            }

            else -> {
                ContestSearchList(
                    modifier = Modifier.padding(innerPadding),
                    contestList = uiState.matches,
                    openContestDetails = openContestDetails,
                    onOpenWebSite = onOpenWebSite
                )
            }
        }
    }
}

@Composable
private fun ContestSearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    SearchBar(
        searchText = searchText,
        placeholderText = stringResource(id = R.string.contest_search_placeholder),
        onSearchTextChanged = onSearchTextChanged,
        onClearText = { onSearchTextChanged("") },
        onNavigateBack = onNavigateBack
    )
}


@Preview
@Composable
private fun Preview() {
    ContestSearchScreen(onNavigateBack = {}, openContestDetails = {}, onOpenWebSite = {})
}