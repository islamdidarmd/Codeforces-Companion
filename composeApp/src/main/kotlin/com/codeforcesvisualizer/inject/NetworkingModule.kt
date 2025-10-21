package com.codeforcesvisualizer.inject

import com.codeforcesvisualizer.shared.data.network.ApiClient
import com.codeforcesvisualizer.shared.data.network.CFApiService
import io.ktor.client.HttpClient
import org.koin.dsl.module

val networkingModule = module {
    single<HttpClient> { _root_ide_package_.com.codeforcesvisualizer.shared.data.network.ApiClient.getHttpClient() }
    single { _root_ide_package_.com.codeforcesvisualizer.shared.data.network.CFApiService(get()) }
}