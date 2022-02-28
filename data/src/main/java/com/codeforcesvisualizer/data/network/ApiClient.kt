package com.codeforcesvisualizer.data.network

import com.codeforcesvisualizer.data.BuildConfig
import com.codeforcesvisualizer.data.config.OKHTTP_CONNECT_TIMEOUT
import com.codeforcesvisualizer.data.config.OKHTTP_READ_TIMEOUT
import com.codeforcesvisualizer.data.config.OKHTTP_WRITE_TIMEOUT
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

fun getOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(OKHTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(OKHTTP_READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(OKHTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(getLoggingInterceptor())
        .build()
}

fun getLoggingInterceptor(): Interceptor {
    return HttpLoggingInterceptor()
        .setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        )
}

fun getRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()
}