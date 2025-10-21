package com.codeforcesvisualizer.shared.domain.usecase

import com.codeforcesvisualizer.core.data.AppError
import com.codeforcesvisualizer.core.data.Either
import com.codeforcesvisualizer.shared.domain.entity.Contest
import com.codeforcesvisualizer.shared.domain.repository.CFRepository

class GetContestByIdUseCase(
    private val cfRepository: CFRepository
) {
    suspend operator fun invoke(id: Int): Either<AppError, Contest> {
        return cfRepository.getContestById(id)
    }
}