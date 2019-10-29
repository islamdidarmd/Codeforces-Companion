package com.codeforcesvisualizer.repository

import com.codeforcesvisualizer.api.ApiClient
import com.codeforcesvisualizer.api.ApiService

open class BaseRepository {
    protected val apiService by lazy {
        ApiClient.getClient().create(ApiService::class.java)
    }
}