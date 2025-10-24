package com.codeforcesvisualizer.shared.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.codeforcesvisualizer.shared.domain.entity.UiThemeMode

interface ThemeRepository {
    suspend fun getUiThemeMode(sharedPreferences: DataStore<Preferences>): UiThemeMode
    suspend fun setUiThemeMode(sharedPreferences: DataStore<Preferences>, uiThemeMode: UiThemeMode)
}