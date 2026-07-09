package com.codeforcesvisualizer.inject

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
private val dataStore: DataStore<Preferences> by lazy {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )
    val path = documentDirectory!!.path + "/$dataStoreFileName"
    PreferenceDataStoreFactory.createWithPath {
        path.toPath()
    }
}

actual fun createPlatformDataStore(): DataStore<Preferences> = dataStore