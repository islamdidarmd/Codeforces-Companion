package com.codeforcesvisualizer.domain.usecase

import com.codeforcesvisualizer.core.data.data.AppError
import com.codeforcesvisualizer.core.data.data.Either
import com.codeforcesvisualizer.domain.entity.Contest
import com.codeforcesvisualizer.domain.repository.CFRepository
import javax.inject.Inject

class FindUserInfoByHandleUseCase @Inject constructor(
    private val cfRepository: CFRepository
) {
    suspend operator fun invoke(id: Int): Either<AppError, Contest> {
        return cfRepository.getContestById(id)
    }
}