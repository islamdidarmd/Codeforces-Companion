package com.codeforcesvisualizer.shared.domain.usecase

import android.content.SharedPreferences
import com.codeforcesvisualizer.shared.domain.entity.UiThemeMode
import com.codeforcesvisualizer.shared.domain.repository.ThemeRepository

class SetUiThemeModeUseCase(
    private val themeRepository: ThemeRepository,
    private val sharedPreferences: SharedPreferences,
) {
    operator fun invoke(uiThemeMode: UiThemeMode) {
        return themeRepository.setUiThemeMode(sharedPreferences, uiThemeMode)
    }
}