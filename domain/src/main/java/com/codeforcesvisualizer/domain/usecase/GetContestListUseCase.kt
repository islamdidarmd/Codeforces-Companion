package com.codeforcesvisualizer.domain.usecase

import com.codeforcesvisualizer.core.data.AppError
import com.codeforcesvisualizer.core.data.Either
import com.codeforcesvisualizer.domain.entity.Contest
import com.codeforcesvisualizer.domain.repository.CFRepository
import javax.inject.Inject

class GetContestListUseCase @Inject constructor(
    private val cfRepository: CFRepository
) {
    suspend operator fun invoke(refresh: Boolean = false): Either<AppError, List<Contest>> {
        return cfRepository.getContestList(refresh)
    }
}