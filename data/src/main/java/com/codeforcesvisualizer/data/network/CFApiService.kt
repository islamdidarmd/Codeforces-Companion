package com.codeforcesvisualizer.data.network

import com.codeforcesvisualizer.data.config.CONTEST_LIST_URL
import com.codeforcesvisualizer.data.model.ContestListResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface CFApiService {
    @GET(value = CONTEST_LIST_URL)
    suspend fun getContestList(): Response<ContestListResponseModel>
}