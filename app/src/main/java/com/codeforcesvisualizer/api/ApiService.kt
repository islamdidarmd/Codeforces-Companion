package com.codeforcesvisualizer.api

import com.codeforcesvisualizer.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET()
    fun getContests(@Url url: String): Call<ContestResponse>

    @GET()
    fun getUSers(@Url url: String): Call<UserResponse>

    @GET()
    fun getUserStatus(@Url url: String): Call<UserStatusResponse>

    @GET()
    fun getUserExtra(@Url url: String): Call<UserExtraResponse>
}