package com.codeforcesvisualizer.shared.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.codeforcesvisualizer.shared.domain.entity.UiThemeMode
import com.codeforcesvisualizer.shared.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.first

class ThemeRepositoryImpl : ThemeRepository {
    private val _themeModeKey = intPreferencesKey("theme_mode")

    override suspend fun getUiThemeMode(sharedPreferences: DataStore<Preferences>): UiThemeMode {
        return when (sharedPreferences.data.first()[_themeModeKey]) {
            1 -> UiThemeMode.Light
            2 -> UiThemeMode.Dark
            else -> UiThemeMode.System
        }
    }

    override suspend fun setUiThemeMode(sharedPreferences: DataStore<Preferences>, uiThemeMode: UiThemeMode) {
        val mode = when (uiThemeMode) {
            UiThemeMode.System -> 0
            UiThemeMode.Light -> 1
            UiThemeMode.Dark -> 2
        }
        sharedPreferences.edit { store ->
            store[_themeModeKey] = mode
        }
    }
}