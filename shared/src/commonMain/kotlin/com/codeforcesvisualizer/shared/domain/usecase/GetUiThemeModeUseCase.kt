package com.codeforcesvisualizer.shared.domain.usecase

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.codeforcesvisualizer.shared.domain.entity.UiThemeMode
import com.codeforcesvisualizer.shared.domain.repository.ThemeRepository

class GetUiThemeModeUseCase(
    private val themeRepository: ThemeRepository,
    private val sharedPreferences: DataStore<Preferences>,
) {
    suspend operator fun invoke(): UiThemeMode {
        return themeRepository.getUiThemeMode(sharedPreferences)
    }
}