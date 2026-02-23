package com.codeforcesvisualizer.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import codeforces_visualizer.composeapp.generated.resources.Res
import codeforces_visualizer.composeapp.generated.resources.enter_handle_hint
import com.codeforcesvisualizer.core.EventLogger
import com.codeforcesvisualizer.core.components.SearchBar
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileSearchScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onOpenWebSite: (String) -> Unit,
    viewModel: ProfileSearchViewModel = koinViewModel()
) {
    val searchTextState by viewModel.searchTextState.collectAsState()
    val userInfoUiState by viewModel.userInfoState.collectAsState()
    val userStatusUiState by viewModel.userStatusState.collectAsState()
    val userRatingsUiState by viewModel.userRatingState.collectAsState()

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
                    viewModel.getUserStatusByHandle(searchTextState)
                    viewModel.getUserRatingByHandle(searchTextState)
                    EventLogger.logEvent(
                        event = "Search User",
                        param = mapOf(
                            "Handle" to searchTextState
                        )
                    )
                }
            )
        }
    ) { innerPadding ->
        ProfileSearchScreen(
            modifier = Modifier.padding(innerPadding),
            userInfoUiState = userInfoUiState,
            userStatusUiState = userStatusUiState,
            userRatingsUiState = userRatingsUiState,
            onOpenWebSite = onOpenWebSite
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
    placeholderText = stringResource(Res.string.enter_handle_hint),
        onSearchTextChanged = onSearchTextChanged,
        onSearch = onSearch,
        onClearText = { onSearchTextChanged("") },
        onNavigateBack = onNavigateBack
    )
}

@Composable
private fun ProfileSearchScreen(
    modifier: Modifier = Modifier,
    userInfoUiState: UserInfoUiState,
    userStatusUiState: UserStatusUiState,
    userRatingsUiState: UserRatingUiState,
    onOpenWebSite: (String) -> Unit
) {
    LazyColumn(modifier = modifier) {
        if (userInfoUiState.loading
            || userInfoUiState.userMessage.isNotBlank()
            || userInfoUiState.user != null
        ) item {
            UserInfoCard(userInfoUiState = userInfoUiState)
        }
        if (userStatusUiState.loading
            || userStatusUiState.userMessage.isNotBlank()
            || userStatusUiState.userStatus != null
        ) {
            item {
                LanguageCard(userStatusUiState = userStatusUiState)
            }
            item {
                VerdictCard(userStatusUiState = userStatusUiState)
            }
            item {
                LevelsCard(userStatusUiState = userStatusUiState)
            }
            item {
                TagsCard(userStatusUiState = userStatusUiState)
            }
            item {
                UnsolvedCard(
                    userStatusUiState = userStatusUiState,
                    onOpenWebSite = onOpenWebSite
                )
            }
            item {
                RatingsCard(userRatingUiState = userRatingsUiState)
            }
        }
    }
}
