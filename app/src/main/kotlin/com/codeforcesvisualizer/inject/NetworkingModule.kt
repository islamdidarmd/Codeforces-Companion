package com.codeforcesvisualizer.inject


import com.codeforcesvisualizer.data.config.BASE_URL
import com.codeforcesvisualizer.data.network.ApiClient
import com.codeforcesvisualizer.data.network.CFApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
object NetworkingModule {
    @Provides
    fun providesOkHttp(): OkHttpClient {
        return ApiClient.getOkHttpClient()
    }

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return ApiClient.getRetrofit(okHttpClient = okHttpClient, BASE_URL)
    }

    @Provides
    fun providesCFApiService(retrofit: Retrofit): CFApiService {
        return retrofit.create(CFApiService::class.java)
    }
}