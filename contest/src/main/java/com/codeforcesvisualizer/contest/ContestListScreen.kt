package com.codeforcesvisualizer.contest

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ContestListScreen(
    modifier: Modifier,
    contestViewModel: ContestViewModel = viewModel()
) {
    val uiState = contestViewModel.uiState.collectAsState(
        initial = ContestListUiState(
            loading = true,
            emptyList(),
            ""
        )
    )

    if (uiState.value.loading) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Preview
@Composable
fun Preview() {
    ContestListScreen(modifier = Modifier)
}