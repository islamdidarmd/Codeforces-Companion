package com.codeforcesvisualizer.inject

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferenceModule = module {
    val dataStoreFileName = "cfv.preferences_pb"
    single<DataStore<Preferences>> {
        val context: Context = androidContext()
        val path = context.filesDir.resolve(dataStoreFileName).absolutePath
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { path.toPath() }
        )
    }
}