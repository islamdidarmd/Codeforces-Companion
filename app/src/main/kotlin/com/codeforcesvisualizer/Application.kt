package com.codeforcesvisualizer

import com.codeforcesvisualizer.inject.appModule
import com.codeforcesvisualizer.inject.networkingModule
import com.codeforcesvisualizer.inject.preferenceModule
import com.codeforcesvisualizer.inject.useCaseModule
import com.codeforcesvisualizer.inject.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : android.app.Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@Application)
            modules(
                networkingModule,
                appModule,
                preferenceModule,
                useCaseModule,
                viewModelModule
            )
        }
    }
}