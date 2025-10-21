package com.codeforcesvisualizer.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.androidx.compose.koinViewModel
import com.codeforcesvisualizer.BuildConfig
import com.codeforcesvisualizer.core.EventLogger
import com.codeforcesvisualizer.core.theme.CFTheme
import com.codeforcesvisualizer.shared.domain.entity.UiThemeMode
import com.codeforcesvisualizer.preference.ThemeManager
import com.codeforcesvisualizer.preference.ThemeManagerViewModel
import com.codeforcesvisualizer.preference.ThemeModeUiState
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
class HomeActivity : AppCompatActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics

        if (!BuildConfig.DEBUG) {
            EventLogger.initialize(::logEvent)
        }

        setContent {
            val themeManager: ThemeManager = koinViewModel<ThemeManagerViewModel>()
            val themeModeUiState by themeManager.themeModeFlow.collectAsState()
            val isDarkTheme = when (themeModeUiState.themeMode) {
                UiThemeMode.System -> isSystemInDarkTheme()
                UiThemeMode.Dark -> true
                UiThemeMode.Light -> false
            }
            firebaseAnalytics.setUserProperty("UiMode", themeModeUiState.themeMode.toString())

            CFTheme(
                isDarkTheme = isDarkTheme
            ) {
                Home(themeManager = themeManager)
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
        Home(themeManager = PreviewThemeManager())
    }
}

private class PreviewThemeManager : ThemeManager {
    private val state = MutableStateFlow(ThemeModeUiState())
    override val themeModeFlow: StateFlow<ThemeModeUiState> = state

    override fun setUiThemeMode(themeMode: UiThemeMode) {
        state.value = state.value.copy(themeMode = themeMode)
    }
}