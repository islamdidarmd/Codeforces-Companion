package com.codeforcesvisualizer.compare

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codeforcesvisualizer.core.data.components.CFAppBar

@Composable
fun CompareHandlesScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    handleOne: String,
    handleTwo: String,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CFAppBar(
                title = "$handleOne,$handleTwo",
                onNavigateBack = onNavigateBack
            )
        }) {

    }
}