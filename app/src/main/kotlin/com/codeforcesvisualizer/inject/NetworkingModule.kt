package com.codeforcesvisualizer.inject
import com.codeforcesvisualizer.data.network.ApiClient
import com.codeforcesvisualizer.data.network.CFApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkingModule {
    @Provides
    @Singleton
    fun providesHttpClient(): HttpClient {
        return ApiClient.getHttpClient()
    }

    @Provides
    @Singleton
    fun providesCFApiService(httpClient: HttpClient): CFApiService {
        return CFApiService(httpClient)
    }
}