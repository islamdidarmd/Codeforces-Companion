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
import com.codeforcesvisualizer.BuildConfig
import com.codeforcesvisualizer.core.EventLogger
import com.codeforcesvisualizer.core.theme.CFTheme
import com.codeforcesvisualizer.domain.entity.UiThemeMode
import com.codeforcesvisualizer.preference.ThemeManagerViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!BuildConfig.DEBUG) {
            firebaseAnalytics = Firebase.analytics
            EventLogger.initialize(::logEvent)
        }

        setContent {
            val themeManagerViewModel: ThemeManagerViewModel = hiltViewModel()
            val themeModeUiState by themeManagerViewModel.themeModeFlow.collectAsState()
            val isDarkTheme = when (themeModeUiState.themeMode) {
                UiThemeMode.System -> isSystemInDarkTheme()
                UiThemeMode.Dark -> true
                UiThemeMode.Light -> false
            }
            firebaseAnalytics.setUserProperty("UiMode", themeModeUiState.themeMode.toString())

            CFTheme(
                isDarkTheme = isDarkTheme
            ) {
                Home(themeManagerViewModel = themeManagerViewModel)
            }
        }
    }

    private fun logEvent(event: String, param: Bundle) {
        firebaseAnalytics.logEvent(event, param)
    }
}

@Preview
@Composable
fun Preview() {
    CFTheme(isDarkTheme = false) {
        Home(themeManagerViewModel = hiltViewModel())
    }
}