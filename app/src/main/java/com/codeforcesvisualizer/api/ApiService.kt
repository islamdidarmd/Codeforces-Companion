package com.codeforcesvisualizer.api

import com.codeforcesvisualizer.model.ContestResponse
import com.codeforcesvisualizer.model.UserResponse
import com.codeforcesvisualizer.model.UserStatus
import com.codeforcesvisualizer.model.UserStatusResponse
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
}