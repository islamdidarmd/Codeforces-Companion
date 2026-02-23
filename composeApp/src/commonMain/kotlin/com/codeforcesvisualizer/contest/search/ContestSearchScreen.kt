package com.codeforcesvisualizer.contest.search

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import codeforces_visualizer.composeapp.generated.resources.Res
import codeforces_visualizer.composeapp.generated.resources.contest_search_placeholder
import com.codeforcesvisualizer.core.components.Center
import com.codeforcesvisualizer.core.components.SearchBar
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ContestSearchScreen(
    modifier: Modifier = Modifier,
    contestSearchViewModel: ContestSearchViewModel = koinViewModel(),
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
    placeholderText = stringResource(Res.string.contest_search_placeholder),
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