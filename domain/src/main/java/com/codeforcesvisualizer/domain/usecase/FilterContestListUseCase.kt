package com.codeforcesvisualizer.domain.usecase

import com.codeforcesvisualizer.core.data.AppError
import com.codeforcesvisualizer.core.data.Either
import com.codeforcesvisualizer.domain.entity.Contest
import com.codeforcesvisualizer.domain.repository.CFRepository

class FilterContestListUseCase(
    private val cfRepository: CFRepository
) {
    suspend operator fun invoke(key: String): Either<AppError, List<Contest>> {
        return cfRepository.filterContestList(key)
    }
}