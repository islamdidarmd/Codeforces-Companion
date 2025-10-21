package com.codeforcesvisualizer.shared.domain.usecase

import com.codeforcesvisualizer.core.data.AppError
import com.codeforcesvisualizer.core.data.Either
import com.codeforcesvisualizer.shared.domain.entity.Contest
import com.codeforcesvisualizer.shared.domain.repository.CFRepository

class GetContestListUseCase(
    private val cfRepository: CFRepository
) {
    suspend operator fun invoke(refresh: Boolean = false): Either<AppError, List<Contest>> {
        return cfRepository.getContestList(refresh)
    }
}