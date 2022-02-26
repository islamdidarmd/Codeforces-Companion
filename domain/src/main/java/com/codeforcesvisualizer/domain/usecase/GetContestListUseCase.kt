package com.codeforcesvisualizer.domain.usecase

import com.codeforcesvisualizer.core.data.AppError
import com.codeforcesvisualizer.core.data.Either
import com.codeforcesvisualizer.domain.entity.Contest
import com.codeforcesvisualizer.domain.repository.CFRepository

class GetContestListUseCase(
    private val cfRepository: CFRepository
) {
    suspend operator fun invoke(): Either<AppError, List<Contest>>{
        return cfRepository.getContestList()
    }
}