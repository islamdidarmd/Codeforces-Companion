package com.codeforcesvisualizer.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.codeforcesvisualizer.core.theme.CFTheme
import com.codeforcesvisualizer.domain.entity.UiThemeMode
import com.codeforcesvisualizer.preference.ThemeManagerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val themeManagerViewModel: ThemeManagerViewModel = hiltViewModel()
            val themeModeUiState by themeManagerViewModel.themeModeFlow.collectAsState()
            val isDarkTheme = when (themeModeUiState.themeMode) {
                UiThemeMode.System -> isSystemInDarkTheme()
                UiThemeMode.Dark -> true
                UiThemeMode.Light -> false
            }

            CFTheme(
                isDarkTheme = isDarkTheme
            ) {
                Home(themeManagerViewModel = themeManagerViewModel)
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    CFTheme(isDarkTheme = false) {
        Home(themeManagerViewModel = hiltViewModel())
    }
}