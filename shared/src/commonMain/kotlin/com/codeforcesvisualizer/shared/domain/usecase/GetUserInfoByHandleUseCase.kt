package com.codeforcesvisualizer.shared.domain.usecase

import com.codeforcesvisualizer.shared.core.AppError
import com.codeforcesvisualizer.shared.core.Either
import com.codeforcesvisualizer.shared.domain.entity.User
import com.codeforcesvisualizer.shared.domain.repository.CFRepository

class GetUserInfoByHandleUseCase(
    private val cfRepository: CFRepository
) {
    suspend operator fun invoke(handle: String): Either<AppError, User> {
        return cfRepository.getUserInfoByHandle(handle)
    }
}