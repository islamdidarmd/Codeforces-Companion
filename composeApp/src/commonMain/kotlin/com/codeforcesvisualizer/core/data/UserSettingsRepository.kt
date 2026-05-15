package com.codeforcesvisualizer.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserSettingsRepository(
    private val dataStore: DataStore<Preferences>
) {
    private val usernameKey = stringPreferencesKey("cf_username")

    val username: Flow<String> = dataStore.data.map { prefs ->
        prefs[usernameKey] ?: ""
    }

    suspend fun setUsername(username: String) {
        dataStore.edit { prefs ->
            prefs[usernameKey] = username.trim()
        }
    }
}
