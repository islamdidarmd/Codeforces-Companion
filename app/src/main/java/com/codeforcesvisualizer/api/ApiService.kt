package com.codeforcesvisualizer.api

import com.codeforcesvisualizer.model.ContestResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET()
    fun getContests(@Url url: String): Call<ContestResponse>
}