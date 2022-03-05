package com.codeforcesvisualizer.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.codeforcesvisualizer.core.data.data.Either

import com.codeforcesvisualizer.data.config.BASE_URL
import com.codeforcesvisualizer.data.datasource.CFRemoteDataSourceImpl
import com.codeforcesvisualizer.data.network.CFApiService
import com.codeforcesvisualizer.data.network.ApiClient.getOkHttpClient
import com.codeforcesvisualizer.data.network.ApiClient.getRetrofit
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class CFRemoteDataSourceTest {
    private val apiService = getRetrofit(
        baseUrl = BASE_URL,
        okHttpClient = getOkHttpClient()
    ).create(CFApiService::class.java)
    private val cfRemoteDataSource = CFRemoteDataSourceImpl(apiService)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `Remote DataSource should return Contest List`() {
        val response = runBlocking {
            return@runBlocking cfRemoteDataSource.getContestList()
        }
        assert(response is Either.Right)
    }
}