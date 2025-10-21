package com.codeforcesvisualizer.inject

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferenceModule = module {
    single<SharedPreferences> {
        val context: Context = androidContext()
        context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }
}