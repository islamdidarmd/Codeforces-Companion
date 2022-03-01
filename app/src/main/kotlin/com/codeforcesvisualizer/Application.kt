package com.codeforcesvisualizer

import android.content.Context
import android.content.SharedPreferences
import com.codeforcesvisualizer.model.ContestResponse
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : android.app.Application() {

    companion object {
        var contestResponse: ContestResponse? = null
        lateinit var mInstance: Application
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this

        if (!BuildConfig.DEBUG) {
        //    Fabric.with(this, Answers(), Crashlytics())
        }
    }
}