package com.codeforcesvisualizer.shared.domain.usecase

import com.codeforcesvisualizer.shared.core.AppError
import com.codeforcesvisualizer.shared.core.Either
import com.codeforcesvisualizer.shared.domain.entity.Contest
import com.codeforcesvisualizer.shared.domain.repository.CFRepository

class GetContestListUseCase(
    private val cfRepository: CFRepository
) {
    suspend operator fun invoke(refresh: Boolean = false): Either<AppError, List<Contest>> {
        return cfRepository.getContestList(refresh)
    }
}