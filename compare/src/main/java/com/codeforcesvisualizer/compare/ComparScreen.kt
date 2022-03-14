package com.codeforcesvisualizer.compare

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.codeforcesvisualizer.core.data.components.CFAppBar

@Composable
fun CompareScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = { CFAppBar(title = stringResource(R.string.compare))}
    ) {

    }
}