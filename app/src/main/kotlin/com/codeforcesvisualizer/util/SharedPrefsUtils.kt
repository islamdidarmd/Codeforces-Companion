package com.codeforcesvisualizer.util

import android.content.Context
import com.codeforcesvisualizer.Application

object SharedPrefsUtils {
    private val prefs by lazy {
        Application.mInstance.getSharedPreferences(Application.mInstance.packageName, Context.MODE_PRIVATE)
    }

    private val prefsEditor by lazy {
        prefs.edit()
    }

    fun saveData(data: String, name: String) {
        prefsEditor.putString(name, data)
        prefsEditor.apply()
    }

    fun getData(name: String, default: String): String? {
        return prefs.getString(name, default)
    }

    fun saveNightMode(enabled: Boolean) {
        prefsEditor.putBoolean("night_mode_enabled", enabled)
        prefsEditor.apply()
    }

    fun getNightMode(): Boolean {
        return prefs.getBoolean("night_mode_enabled", false)
    }
}