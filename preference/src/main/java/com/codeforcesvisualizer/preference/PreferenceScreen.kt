package com.codeforcesvisualizer.preference

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.codeforcesvisualizer.core.data.components.CFAppBar

@Composable
fun PreferenceScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = { CFAppBar(title = stringResource(R.string.preferences)) }
    ) {

    }
}

@Preview
@Composable
private fun Preview() {
    PreferenceScreen(
        onNavigateBack = {}
    )
}