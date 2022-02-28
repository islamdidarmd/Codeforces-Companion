package com.codeforcesvisualizer.contest

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ContestListScreen(contestViewModel: ContestViewModel = viewModel()) {
    Text(text = "Hello from Contest Screen")
}

@Preview
@Composable
fun Preview() {
    ContestListScreen()
}