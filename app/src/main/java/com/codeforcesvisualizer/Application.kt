package com.codeforcesvisualizer

import android.content.Context
import android.content.SharedPreferences
import com.codeforcesvisualizer.model.ContestResponse
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent
import io.fabric.sdk.android.Fabric
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class Application : android.app.Application() {
    companion object {
        var contestResponse: ContestResponse? = null
        var contestResponseLoadedFromCache = false
        lateinit var sharedPreferences: SharedPreferences

        fun saveData(data: String, name: String) {
            val editor = sharedPreferences.edit()
            editor.putString(name, data)
            editor.apply()
        }

        fun getData(name: String, default: String): String {
            return sharedPreferences.getString(name, default)
        }

        fun logEvent(event: String) {
            if (BuildConfig.DEBUG) return
            Answers.getInstance().logCustom(CustomEvent(event))
        }
    }

    override fun onCreate() {
        super.onCreate()

        sharedPreferences = getSharedPreferences(packageName,
                Context.MODE_PRIVATE)

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/lato.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build())

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Crashlytics())
        }
    }
}