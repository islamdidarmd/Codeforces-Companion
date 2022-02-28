package com.codeforcesvisualizer.data.repository

import com.codeforcesvisualizer.core.data.AppError
import com.codeforcesvisualizer.core.data.Either
import com.codeforcesvisualizer.data.datasource.CFRemoteDataSource
import com.codeforcesvisualizer.domain.entity.Contest
import com.codeforcesvisualizer.domain.repository.CFRepository

class CFRepositoryImpl(
    private val cfRemoteDataSource: CFRemoteDataSource
) : CFRepository {
    override suspend fun getContestList(): Either<AppError, List<Contest>> {
        val data = cfRemoteDataSource.getContestList()
        return if (data is Either.Right) Either.Right(data = data.data.toEntity())
        else Either.Left(data = (data as Either.Left).data)
    }
}