package com.codeforcesvisualizer.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.codeforcesvisualizer.core.data.Either
import com.codeforcesvisualizer.data.datasource.CFRemoteDataSourceImpl
import com.codeforcesvisualizer.data.network.CFApiService
import com.codeforcesvisualizer.data.network.ApiClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.After
import org.junit.Rule
import org.junit.Test

class CFRemoteDataSourceTest {
    private val httpClient = ApiClient.getHttpClient()
    private val apiService = CFApiService(httpClient)
    private val cfRemoteDataSource = CFRemoteDataSourceImpl(apiService)

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun `Remote DataSource should return Contest List`() {
        val response = runBlocking {
            return@runBlocking cfRemoteDataSource.getContestList()
        }
        assertTrue(response is Either.Right<*, *>)
    }

    @After
    fun tearDown() {
        httpClient.close()
    }
}