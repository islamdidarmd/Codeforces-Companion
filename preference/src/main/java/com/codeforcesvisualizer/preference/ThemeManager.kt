package com.codeforcesvisualizer.preference

import com.codeforcesvisualizer.shared.domain.entity.UiThemeMode
import kotlinx.coroutines.flow.StateFlow

interface ThemeManager {
    val themeModeFlow: StateFlow<ThemeModeUiState>
    fun setUiThemeMode(themeMode: UiThemeMode)
}
