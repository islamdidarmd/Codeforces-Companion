package com.codeforcesvisualizer.webview

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun CFWebViewScreen(
    modifier: Modifier = Modifier,
    link: String,
    onNavigateBack: () -> Unit,
)