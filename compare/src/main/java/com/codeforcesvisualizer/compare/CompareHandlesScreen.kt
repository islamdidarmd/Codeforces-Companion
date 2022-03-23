package com.codeforcesvisualizer.compare

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeforcesvisualizer.core.data.components.CFAppBar

@Composable
fun CompareHandlesScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    handleOne: String,
    handleTwo: String,
    handlesViewModel: CompareHandlesViewModel = hiltViewModel()
) {
    val userRatingsUiState by handlesViewModel.userRatingState.collectAsState()
    val userStatusUiState by handlesViewModel.userStatusState.collectAsState()

    handlesViewModel.getUserRatingByHandle(handle1 = handleOne, handle2 = handleTwo)
    handlesViewModel.getUserStatusByHandle(handle1 = handleOne, handle2 = handleTwo)

    Scaffold(
        modifier = modifier,
        topBar = {
            CFAppBar(
                title = "$handleOne and $handleTwo",
                onNavigateBack = onNavigateBack
            )
        }) { innerPadding ->
        CompareHandleScreen(
            modifier = Modifier.padding(innerPadding),
            userRatingUiState = userRatingsUiState,
            userStatusUiState = userStatusUiState,
            handle1 = handleOne,
            handle2 = handleTwo,
        )
    }
}

@Composable
fun CompareHandleScreen(
    modifier: Modifier = Modifier,
    userRatingUiState: UserRatingUiState,
    userStatusUiState: UserStatusUiState,
    handle1: String,
    handle2: String,
) {
    LazyColumn(modifier = modifier) {
        item {
            RatingsCard(
                userRatingUiState = userRatingUiState,
                handle1 = handle1,
                handle2 = handle2
            )
        }
        item {
            ContestsCard(
                userRatingUiState = userRatingUiState,
                handle1 = handle1,
                handle2 = handle2
            )
        }
        item {
            UpsAndDownCard(
                userRatingUiState = userRatingUiState,
                handle1 = handle1,
                handle2 = handle2
            )
        }
        item {
            RanksCard(
                userRatingUiState = userRatingUiState,
                handle1 = handle1,
                handle2 = handle2
            )
        }

        item {
            TriedAndSolved(
                userStatusUiState = userStatusUiState,
                handle1 = handle1,
                handle2 = handle2
            )
        }
    }
}