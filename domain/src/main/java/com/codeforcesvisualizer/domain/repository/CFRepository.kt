package com.codeforcesvisualizer.domain.repository

import com.codeforcesvisualizer.core.data.AppError
import com.codeforcesvisualizer.core.data.Either
import com.codeforcesvisualizer.domain.entity.Contest

interface CFRepository {
    fun getContestList(): Either<AppError, List<Contest>>
}