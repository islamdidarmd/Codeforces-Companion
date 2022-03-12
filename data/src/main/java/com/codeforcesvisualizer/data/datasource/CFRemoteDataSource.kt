package com.codeforcesvisualizer.data.datasource

import com.codeforcesvisualizer.core.data.data.AppError
import com.codeforcesvisualizer.core.data.data.Either
import com.codeforcesvisualizer.core.data.data.InvalidApiResponseError
import com.codeforcesvisualizer.core.data.data.ServerConnectionResponseError
import com.codeforcesvisualizer.data.model.ContestListResponseModel
import com.codeforcesvisualizer.data.model.UserInfoResponseModel
import com.codeforcesvisualizer.data.model.UserStatusResponseModel
import com.codeforcesvisualizer.data.network.CFApiService
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CFRemoteDataSource {
    suspend fun getContestList(): Either<AppError, ContestListResponseModel>
    suspend fun getUserInfoByHandle(handle: String): Either<AppError, UserInfoResponseModel>
    suspend fun getUserStatusByHandle(handle: String): Either<AppError, UserStatusResponseModel>
}

class CFRemoteDataSourceImpl @Inject constructor(
    private val api: CFApiService
) : CFRemoteDataSource {
    override suspend fun getContestList(): Either<AppError, ContestListResponseModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getContestList()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Either.Right(data = body)
                } else {
                    if(response.errorBody() == null){
                        return@withContext Either.Left(data = InvalidApiResponseError())
                    }

                    val moshi = Moshi.Builder().build()
                    val adapter = moshi.adapter(ContestListResponseModel::class.java)
                    val errorBody = adapter.fromJson(response.errorBody()!!.source())
                    Either.Left(data = AppError(message = errorBody?.comment ?: ""))
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                else Either.Left(data = ServerConnectionResponseError())
            }
        }
    }

    override suspend fun getUserInfoByHandle(handle: String): Either<AppError, UserInfoResponseModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getUserInfoByHandle(handle)
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Either.Right(data = body)
                } else {
                    if(response.errorBody() == null){
                        return@withContext Either.Left(data = InvalidApiResponseError())
                    }

                    val moshi = Moshi.Builder().build()
                    val adapter = moshi.adapter(UserInfoResponseModel::class.java)
                    val errorBody = adapter.fromJson(response.errorBody()!!.source())
                    Either.Left(data = AppError(message = errorBody?.comment ?: ""))
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                else Either.Left(data = ServerConnectionResponseError())
            }
        }
    }

    override suspend fun getUserStatusByHandle(handle: String): Either<AppError, UserStatusResponseModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getUserStatusByHandle(handle)
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Either.Right(data = body)
                } else {
                    if(response.errorBody() == null){
                        return@withContext Either.Left(data = InvalidApiResponseError())
                    }

                    val moshi = Moshi.Builder().build()
                    val adapter = moshi.adapter(UserStatusResponseModel::class.java)
                    val errorBody = adapter.fromJson(response.errorBody()!!.source())
                    Either.Left(data = AppError(message = errorBody?.comment ?: ""))
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                else Either.Left(data = ServerConnectionResponseError())
            }
        }
    }
}