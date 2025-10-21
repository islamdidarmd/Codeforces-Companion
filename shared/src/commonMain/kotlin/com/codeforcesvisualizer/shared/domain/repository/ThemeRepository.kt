package com.codeforcesvisualizer.shared.domain.repository

import android.content.SharedPreferences
import com.codeforcesvisualizer.shared.domain.entity.UiThemeMode

interface ThemeRepository {
    fun getUiThemeMode(sharedPreferences: SharedPreferences): UiThemeMode
    fun setUiThemeMode(sharedPreferences: SharedPreferences, uiThemeMode: UiThemeMode)
}