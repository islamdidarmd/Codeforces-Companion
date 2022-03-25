package com.codeforcesvisualizer.preference

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeforcesvisualizer.core.data.components.CFAppBar
import com.codeforcesvisualizer.core.data.components.HeightSpacer

@Composable
fun PreferenceScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    themeManagerViewModel: ThemeManagerViewModel
) {
    val themeModeUiState by themeManagerViewModel.themeModeFlow.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            CFAppBar(
                title = stringResource(R.string.preferences),
                onNavigateBack = onNavigateBack
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item {
                AppearanceSection(
                    themeMode = themeModeUiState.themeMode,
                    onThemeModeChanged = { selectedThemeMode ->
                        themeManagerViewModel.setUiThemeMode(selectedThemeMode)
                    }
                )
            }
            item {
                Divider()
            }
            item {
                OtherSection()
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PreferenceScreen(
        onNavigateBack = {},
        themeManagerViewModel = hiltViewModel()
    )
}