package com.codeforcesvisualizer.profile

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codeforcesvisualizer.core.data.components.CFAppBar
import com.codeforcesvisualizer.core.data.components.SearchBar

@Composable
fun ProfileSearchScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            ProfileSearchBar(
                searchText = "",
                onNavigateBack = onNavigateBack,
                onSearchTextChanged = {},
                onSearch = {}
            )
        }
    ) {

    }
}

@Composable
private fun ProfileSearchBar(
    modifier: Modifier = Modifier,
    searchText: String,
    onNavigateBack: () -> Unit,
    onSearchTextChanged: (String) -> Unit,
    onSearch: () -> Unit,
) {
    SearchBar(
        searchText = searchText,
        onSearchTextChanged = onSearchTextChanged,
        onSearch = onSearch,
        onClearText = { onSearchTextChanged("") },
        onNavigateBack = onNavigateBack
    )
}