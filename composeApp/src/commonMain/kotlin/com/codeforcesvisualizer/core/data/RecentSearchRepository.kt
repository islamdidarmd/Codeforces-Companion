package com.codeforcesvisualizer.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecentSearchRepository(
    private val dataStore: DataStore<Preferences>
) {
    private val recentSearchesKey = stringPreferencesKey("recent_searches")
    private val maxItems = 10

    val recentSearches: Flow<List<String>> = dataStore.data.map { prefs ->
        prefs[recentSearchesKey]?.split(",")?.filter { it.isNotBlank() } ?: emptyList()
    }

    suspend fun addSearch(handle: String) {
        val trimmed = handle.trim()
        if (trimmed.isBlank()) return
        dataStore.edit { prefs ->
            val existing = prefs[recentSearchesKey]?.split(",")?.filter { it.isNotBlank() }?.toMutableList() ?: mutableListOf()
            existing.remove(trimmed)
            existing.add(0, trimmed)
            prefs[recentSearchesKey] = existing.take(maxItems).joinToString(",")
        }
    }

    suspend fun removeSearch(handle: String) {
        dataStore.edit { prefs ->
            val existing = prefs[recentSearchesKey]?.split(",")?.filter { it.isNotBlank() }?.toMutableList() ?: mutableListOf()
            existing.remove(handle.trim())
            prefs[recentSearchesKey] = existing.joinToString(",")
        }
    }

    suspend fun clearAll() {
        dataStore.edit { prefs ->
            prefs.remove(recentSearchesKey)
        }
    }
}
