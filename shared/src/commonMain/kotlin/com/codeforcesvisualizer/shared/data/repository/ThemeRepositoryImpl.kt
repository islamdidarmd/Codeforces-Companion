package com.codeforcesvisualizer.shared.data.repository

import android.content.SharedPreferences
import com.codeforcesvisualizer.shared.domain.entity.UiThemeMode
import com.codeforcesvisualizer.shared.domain.repository.ThemeRepository

class ThemeRepositoryImpl : ThemeRepository {
    private val _themeModeKey = "theme_mode"

    override fun getUiThemeMode(sharedPreferences: SharedPreferences): UiThemeMode {
        return when (sharedPreferences.getInt(_themeModeKey, 0)) {
            1 -> UiThemeMode.Light
            2 -> UiThemeMode.Dark
            else -> UiThemeMode.System
        }
    }

    override fun setUiThemeMode(sharedPreferences: SharedPreferences, uiThemeMode: UiThemeMode) {
        val editor = sharedPreferences.edit()
        when (uiThemeMode) {
            UiThemeMode.System -> editor.putInt(_themeModeKey, 0)
            UiThemeMode.Light -> editor.putInt(_themeModeKey, 1)
            UiThemeMode.Dark -> editor.putInt(_themeModeKey, 2)
        }
        editor.apply()
    }
}