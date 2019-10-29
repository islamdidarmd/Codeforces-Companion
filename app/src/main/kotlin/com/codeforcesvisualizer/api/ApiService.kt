package com.codeforcesvisualizer.api

import com.codeforcesvisualizer.model.*
import com.codeforcesvisualizer.util.CONTEST_LIST_URL
import com.codeforcesvisualizer.util.USER_EXTRA_URL
import com.codeforcesvisualizer.util.USER_STATUS_URL
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryName
import retrofit2.http.Url

interface ApiService {
    @GET(CONTEST_LIST_URL)
    suspend fun getContestsAsync(): Response<ContestResponse>

    @GET
    suspend fun getUsersAsync(@Url url: String): Response<UserResponse>

    @GET(USER_STATUS_URL)
    suspend fun getUserStatusAsync(@Query("handle") handle: String): Response<UserStatusResponse>

    @GET(USER_EXTRA_URL)
    suspend fun getUserExtraAsync(@Query("handle") handle: String): Response<UserExtraResponse>
}