package com.codeforcesvisualizer

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : android.app.Application() {
    override fun onCreate() {
        super.onCreate()
    }
}