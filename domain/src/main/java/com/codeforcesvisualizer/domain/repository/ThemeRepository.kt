package com.codeforcesvisualizer.domain.repository

import android.content.SharedPreferences
import com.codeforcesvisualizer.domain.entity.UiThemeMode

interface ThemeRepository {
    fun getUiThemeMode(sharedPreferences: SharedPreferences): UiThemeMode
    fun setUiThemeMode(sharedPreferences: SharedPreferences, uiThemeMode: UiThemeMode)
}