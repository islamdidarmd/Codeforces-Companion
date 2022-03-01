package com.codeforcesvisualizer.domain.repository

import com.codeforcesvisualizer.core.data.data.AppError
import com.codeforcesvisualizer.core.data.data.Either
import com.codeforcesvisualizer.domain.entity.Contest

interface CFRepository {
    suspend fun getContestList(): Either<AppError, List<Contest>>
}