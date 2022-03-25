package com.codeforcesvisualizer.domain.usecase

import com.codeforcesvisualizer.core.data.AppError
import com.codeforcesvisualizer.core.data.Either
import com.codeforcesvisualizer.domain.entity.Contest
import com.codeforcesvisualizer.domain.repository.CFRepository
import javax.inject.Inject

class GetContestByIdUseCase @Inject constructor(
    private val cfRepository: CFRepository
) {
    suspend operator fun invoke(id: Int): Either<AppError, Contest> {
        return cfRepository.getContestById(id)
    }
}