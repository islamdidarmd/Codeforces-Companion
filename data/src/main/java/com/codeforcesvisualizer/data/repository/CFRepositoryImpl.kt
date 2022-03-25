package com.codeforcesvisualizer.data.repository

import com.codeforcesvisualizer.core.data.AppError
import com.codeforcesvisualizer.core.data.DataNotFoundError
import com.codeforcesvisualizer.core.data.Either
import com.codeforcesvisualizer.core.data.MatchingDataNotFoundError
import com.codeforcesvisualizer.data.datasource.CFRemoteDataSource
import com.codeforcesvisualizer.domain.entity.Contest
import com.codeforcesvisualizer.domain.entity.User
import com.codeforcesvisualizer.domain.entity.UserRating
import com.codeforcesvisualizer.domain.entity.UserStatus
import com.codeforcesvisualizer.domain.repository.CFRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CFRepositoryImpl @Inject constructor(
    private val cfRemoteDataSource: CFRemoteDataSource
) : CFRepository {
    private val _contestList = mutableListOf<Contest>()

    override suspend fun getContestList(refresh: Boolean): Either<AppError, List<Contest>> {
        if (!refresh && _contestList.isNotEmpty()) return Either.Right(data = _contestList)

        val data = cfRemoteDataSource.getContestList()
        return if (data is Either.Right) {
            _contestList.apply {
                clear()
                addAll(data.data.toEntity())
            }
            Either.Right(data = _contestList)
        } else Either.Left(data = (data as Either.Left).data)
    }

    override suspend fun getUserInfoByHandle(handle: String): Either<AppError, User> {
        val data = cfRemoteDataSource.getUserInfoByHandle(handle.trim())
        return if (data is Either.Right) Either.Right(data = data.data.toEntity().first())
        else Either.Left(data = (data as Either.Left).data)
    }

    override suspend fun getUserStatusByHandle(handle: String): Either<AppError, List<UserStatus>> {
        val data = cfRemoteDataSource.getUserStatusByHandle(handle.trim())
        return if (data is Either.Right) Either.Right(data = data.data.toEntity())
        else Either.Left(data = (data as Either.Left).data)
    }

    override suspend fun getUserRatingByHandle(handle: String): Either<AppError, List<UserRating>> {
        val data = cfRemoteDataSource.getUserRatingByHandle(handle.trim())
        return if (data is Either.Right) Either.Right(data = data.data.toEntity())
        else Either.Left(data = (data as Either.Left).data)
    }

    override suspend fun getContestById(id: Int): Either<AppError, Contest> {
        return withContext(Dispatchers.IO) {
            val contest = _contestList.find { contest -> contest.id == id }
            if (contest != null) Either.Right(data = contest)
            else Either.Left(data = DataNotFoundError())
        }
    }

    override suspend fun filterContestList(key: String): Either<AppError, List<Contest>> {
        if (key.isBlank()) return Either.Left(data = AppError(""))

        return withContext(Dispatchers.IO) {
            val filtered = _contestList.filter { contest ->
                contest.name.contains(key, ignoreCase = true) || contest.type.contains(
                    key,
                    ignoreCase = true
                )
            }
            if (filtered.isNotEmpty()) Either.Right(data = filtered)
            else Either.Left(data = MatchingDataNotFoundError())
        }
    }
}