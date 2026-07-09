package com.codeforcesvisualizer.inject

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.koin.dsl.module

const val dataStoreFileName = "cfv.preferences_pb"
val preferenceModule = module {
    single<DataStore<Preferences>> { createPlatformDataStore() }
}