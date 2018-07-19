package com.codeforcesvisualizer

import android.content.Context
import android.content.SharedPreferences
import com.codeforcesvisualizer.model.ContestResponse

class Application : android.app.Application() {
    companion object {
        var contestResponse: ContestResponse? = null
        lateinit var sharedPreferences: SharedPreferences

        fun saveData(data: String, name: String) {
            val editor = sharedPreferences.edit()
            editor.putString(name, data)
            editor.apply()
        }

        fun getData(name: String, default: String): String {
            return sharedPreferences.getString(name, default)
        }
    }

    override fun onCreate() {
        super.onCreate()

        sharedPreferences = getSharedPreferences(packageName,
                Context.MODE_PRIVATE)
    }
}