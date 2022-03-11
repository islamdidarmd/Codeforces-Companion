package com.codeforcesvisualizer.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeforcesvisualizer.core.data.components.SearchBar

@Composable
fun ProfileSearchScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    viewModel: ProfileSearchViewModel = hiltViewModel()
) {
    val searchTextState by viewModel.searchTextState.collectAsState()
    val userInfoUiState by viewModel.userInfoState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            ProfileSearchBar(
                searchText = searchTextState,
                onNavigateBack = onNavigateBack,
                onSearchTextChanged = { text ->
                    viewModel.onSearchTextChanged(text)
                },
                onSearch = {
                    viewModel.getUserInfoByHandle(searchTextState)
                }
            )
        }
    ) { innerPadding ->
        ProfileSearchScreen(
            modifier = Modifier.padding(innerPadding),
            userInfoUiState = userInfoUiState
        )
    }
}

@Composable
private fun ProfileSearchBar(
    modifier: Modifier = Modifier,
    searchText: String,
    onNavigateBack: () -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onSearch: () -> Unit,
) {
    SearchBar(
        modifier = modifier,
        searchText = searchText,
        placeholderText = stringResource(R.string.enter_handle_hint),
        onSearchTextChanged = onSearchTextChanged,
        onSearch = onSearch,
        onClearText = { onSearchTextChanged("") },
        onNavigateBack = onNavigateBack
    )
}

@Composable
private fun ProfileSearchScreen(
    modifier: Modifier = Modifier,
    userInfoUiState: UserInfoUiState
) {
    LazyColumn(modifier = modifier) {
        if (userInfoUiState.loading
            || userInfoUiState.userMessage.isNotBlank()
            || userInfoUiState.user != null
        ) item {
            UserInfoCard(userInfoUiState = userInfoUiState)
        }
    }
}
