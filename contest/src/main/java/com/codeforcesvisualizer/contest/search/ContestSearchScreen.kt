package com.codeforcesvisualizer.contest.search

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeforcesvisualizer.contest.list.ContestViewModel
import com.codeforcesvisualizer.core.data.components.CFLoadingIndicator
import com.codeforcesvisualizer.core.data.components.Center
import com.codeforcesvisualizer.core.data.components.SearchBar

@Composable
fun ContestSearchScreen(
    modifier: Modifier = Modifier,
    contestSearchViewModel: ContestSearchViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val searchText by contestSearchViewModel.searchTextFlow.collectAsState("")
    val uiState by contestSearchViewModel.uiState.collectAsState(ContestSearchUiState())

    Scaffold(
        modifier = modifier,
        topBar = {
            ContestSearchBar(
                searchText = searchText,
                onSearchTextChanged = { text -> contestSearchViewModel.onSearchTextChanged(text) },
                navigateUp = navigateUp
            )
        }
    ) { innerPadding ->
        when {
            /*uiState.loading -> {
                Center(modifier = Modifier.padding(innerPadding)) {
                    CFLoadingIndicator()
                }
            }*/

            uiState.userMessage.isNotBlank() -> {
                Center(modifier = modifier.padding(innerPadding)) {
                    Text(text = uiState.userMessage)
                }
            }

            else -> {
                ContestSearchList(
                    modifier = Modifier.padding(innerPadding),
                    contestList = uiState.matches
                )
            }
        }
    }
}

@Composable
internal fun ContestSearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
    navigateUp: () -> Unit
) {
    SearchBar(
        searchText = searchText,
        placeholderText = "Enter Contest name of type",
        onSearchTextChanged = onSearchTextChanged,
        onClearText = { onSearchTextChanged("") },
        onNavigateBack = navigateUp
    )
}


@Preview
@Composable
private fun Preview() {
    ContestSearchScreen(navigateUp = {})
}