package com.codeforcesvisualizer.shared.domain.repository

import com.codeforcesvisualizer.core.data.AppError
import com.codeforcesvisualizer.core.data.Either
import com.codeforcesvisualizer.shared.domain.entity.Contest
import com.codeforcesvisualizer.shared.domain.entity.User
import com.codeforcesvisualizer.shared.domain.entity.UserRating
import com.codeforcesvisualizer.shared.domain.entity.UserStatus

interface CFRepository {
    suspend fun getContestList(refresh: Boolean = true): Either<AppError, List<Contest>>

    suspend fun getContestById(id: Int): Either<AppError, Contest>

    suspend fun filterContestList(key: String): Either<AppError, List<Contest>>

    suspend fun getUserInfoByHandle(handle: String): Either<AppError, User>

    suspend fun getUserStatusByHandle(handle: String): Either<AppError, List<UserStatus>>

    suspend fun getUserRatingByHandle(handle: String): Either<AppError, List<UserRating>>
}