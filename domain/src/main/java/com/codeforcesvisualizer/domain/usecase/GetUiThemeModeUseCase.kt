package com.codeforcesvisualizer.domain.usecase

import android.content.SharedPreferences
import com.codeforcesvisualizer.domain.entity.UiThemeMode
import com.codeforcesvisualizer.domain.repository.ThemeRepository
import javax.inject.Inject

class GetUiThemeModeUseCase @Inject constructor(
    private val themeRepository: ThemeRepository,
    private val sharedPreferences: SharedPreferences,
) {
    operator fun invoke(): UiThemeMode {
        return themeRepository.getUiThemeMode(sharedPreferences)
    }
}