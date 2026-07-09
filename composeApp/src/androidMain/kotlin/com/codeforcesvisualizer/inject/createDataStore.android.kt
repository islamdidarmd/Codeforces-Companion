package com.codeforcesvisualizer.inject

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.codeforcesvisualizer.Application
import okio.Path.Companion.toPath

private val dataStore: DataStore<Preferences> by lazy {
    PreferenceDataStoreFactory.createWithPath {
        Application.context.filesDir.resolve(dataStoreFileName).absolutePath.toPath()
    }
}

actual fun createPlatformDataStore(): DataStore<Preferences> = dataStore