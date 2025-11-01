package com.codeforcesvisualizer

import android.content.Context
import com.codeforcesvisualizer.inject.appModule
import com.codeforcesvisualizer.inject.networkingModule
import com.codeforcesvisualizer.inject.preferenceModule
import com.codeforcesvisualizer.inject.useCaseModule
import com.codeforcesvisualizer.inject.viewModelModule

class Application : android.app.Application() {
    companion object {
        @JvmStatic
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        context = this
    }
}