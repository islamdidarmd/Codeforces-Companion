package com.codeforcesvisualizer.data.datasource

import com.codeforcesvisualizer.core.data.data.AppError
import com.codeforcesvisualizer.core.data.data.Either
import com.codeforcesvisualizer.core.data.data.InvalidApiResponseError
import com.codeforcesvisualizer.core.data.data.ServerConnectionResponseError
import com.codeforcesvisualizer.data.model.ContestListResponseModel
import com.codeforcesvisualizer.data.model.UserInfoResponseModel
import com.codeforcesvisualizer.data.network.CFApiService
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CFRemoteDataSource {
    suspend fun getContestList(): Either<AppError, ContestListResponseModel>
    suspend fun getUserInfoByHandle(handle: String): Either<AppError, UserInfoResponseModel>
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
                    Either.Left(data = InvalidApiResponseError())
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
                    Either.Left(data = InvalidApiResponseError())
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                else Either.Left(data = ServerConnectionResponseError())
            }
        }
    }
}