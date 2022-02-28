package com.codeforcesvisualizer.data.datasource

import com.codeforcesvisualizer.core.data.AppError
import com.codeforcesvisualizer.core.data.Either
import com.codeforcesvisualizer.core.data.InvalidApiResponseError
import com.codeforcesvisualizer.data.model.ContestListResponse
import com.codeforcesvisualizer.data.network.CFApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CancellationException

interface CFRemoteDataSource {
    suspend fun getContestList(): Either<AppError, ContestListResponse>
}

class CFRemoteDataSourceImpl(
    private val api: CFApiService
) : CFRemoteDataSource {
    override suspend fun getContestList(): Either<AppError, ContestListResponse> {
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
                else Either.Left(data = AppError(message = e.message))
            }
        }
    }
}