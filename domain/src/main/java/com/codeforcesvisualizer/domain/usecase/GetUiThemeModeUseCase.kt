package com.codeforcesvisualizer.domain.usecase

import android.content.SharedPreferences
import com.codeforcesvisualizer.domain.entity.UiThemeMode
import com.codeforcesvisualizer.domain.repository.ThemeRepository

class GetUiThemeModeUseCase(
    private val themeRepository: ThemeRepository,
    private val sharedPreferences: SharedPreferences,
) {
    operator fun invoke(): UiThemeMode {
        return themeRepository.getUiThemeMode(sharedPreferences)
    }
}