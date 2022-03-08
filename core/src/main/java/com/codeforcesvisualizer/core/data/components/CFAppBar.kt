package com.codeforcesvisualizer.core.data.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun CFAppBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigateBack: (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null,
) {
    if (onNavigateBack != null) {
        CFAppBarWithBackNavigation(
            modifier = modifier,
            title = title,
            onNavigateBack = onNavigateBack,
            actions = actions
        )
    } else {
        CFAppBar(
            modifier = modifier,
            title = title,
            actions = { actions?.invoke() }
        )
    }
}

@Composable
private fun CFAppBar(
    modifier: Modifier = Modifier,
    title: String,
    actions: @Composable (() -> Unit)? = null,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis)
        },
        actions = { actions?.invoke() }
    )
}

@Composable
private fun CFAppBarWithBackNavigation(
    modifier: Modifier = Modifier,
    title: String,
    onNavigateBack: () -> Unit,
    actions: @Composable (() -> Unit)? = null,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis)
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        actions = { actions?.invoke() }
    )
}