package com.codeforcesvisualizer.inject

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

expect fun createPlatformDataStore(): DataStore<Preferences>