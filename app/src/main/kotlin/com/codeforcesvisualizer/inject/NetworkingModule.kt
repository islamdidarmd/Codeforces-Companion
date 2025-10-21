package com.codeforcesvisualizer.inject

import com.codeforcesvisualizer.data.network.ApiClient
import com.codeforcesvisualizer.data.network.CFApiService
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkingModule = module {
    single<HttpClient> { ApiClient.getHttpClient() }
    single { CFApiService(get()) }
}