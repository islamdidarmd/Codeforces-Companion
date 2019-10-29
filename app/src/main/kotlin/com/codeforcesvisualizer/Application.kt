package com.codeforcesvisualizer

import android.content.Context
import android.content.SharedPreferences
import com.codeforcesvisualizer.model.ContestResponse
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent
import io.fabric.sdk.android.Fabric


class Application : android.app.Application() {

    companion object {
        var contestResponse: ContestResponse? = null
        lateinit var mInstance: Application
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this

        if (!BuildConfig.DEBUG) {
            Fabric.with(this, Answers(), Crashlytics())
        }
    }
}