package com.codeforcesvisualizer.domain.repository

import com.codeforcesvisualizer.core.data.data.AppError
import com.codeforcesvisualizer.core.data.data.Either
import com.codeforcesvisualizer.domain.entity.Contest
import com.codeforcesvisualizer.domain.entity.User

interface CFRepository {
    suspend fun getContestList(refresh: Boolean = true): Either<AppError, List<Contest>>

    suspend fun getContestById(id: Int): Either<AppError, Contest>

    suspend fun filterContestList(key: String): Either<AppError, List<Contest>>

    suspend fun getUserInfoByHandle(handle: String): Either<AppError, User>
}