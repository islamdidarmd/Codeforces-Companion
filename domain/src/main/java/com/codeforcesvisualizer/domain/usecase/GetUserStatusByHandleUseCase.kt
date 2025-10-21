package com.codeforcesvisualizer.domain.usecase

import com.codeforcesvisualizer.core.data.AppError
import com.codeforcesvisualizer.core.data.Either
import com.codeforcesvisualizer.domain.entity.UserStatus
import com.codeforcesvisualizer.domain.repository.CFRepository

class GetUserStatusByHandleUseCase(
    private val cfRepository: CFRepository
) {
    suspend operator fun invoke(handle: String): Either<AppError, List<UserStatus>> {
        return cfRepository.getUserStatusByHandle(handle)
    }
}