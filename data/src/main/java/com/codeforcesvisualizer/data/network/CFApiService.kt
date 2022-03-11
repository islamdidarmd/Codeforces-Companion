package com.codeforcesvisualizer.data.network

import com.codeforcesvisualizer.data.config.CONTEST_LIST_URL
import com.codeforcesvisualizer.data.config.USER_INFO_URL
import com.codeforcesvisualizer.data.config.USER_STATUS_URL
import com.codeforcesvisualizer.data.model.ContestListResponseModel
import com.codeforcesvisualizer.data.model.UserInfoResponseModel
import com.codeforcesvisualizer.data.model.UserStatusResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CFApiService {
    @GET(CONTEST_LIST_URL)
    suspend fun getContestList(): Response<ContestListResponseModel>

    @GET(USER_INFO_URL)
    suspend fun getUserInfoByHandle(@Query("handles") handle: String): Response<UserInfoResponseModel>

    @GET(USER_STATUS_URL)
    suspend fun getUserStatusByHandle(@Query("handle") handle: String): Response<UserStatusResponseModel>
}