package com.codeforcesvisualizer.profile

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codeforcesvisualizer.core.data.components.CFAppBar

@Composable
fun ProfileSearchScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = { CFAppBar(title = "Profile") }
    ) {

    }
}