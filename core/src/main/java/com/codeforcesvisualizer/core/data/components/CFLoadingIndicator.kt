package com.codeforcesvisualizer.core.data.components

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CFLoadingIndicator(modifier: Modifier = Modifier) {
    Center(modifier = modifier) {
        CircularProgressIndicator()
    }
}