package com.codeforcesvisualizer.domain.usecase

import com.codeforcesvisualizer.core.data.AppError
import com.codeforcesvisualizer.core.data.Either
import com.codeforcesvisualizer.domain.entity.User
import com.codeforcesvisualizer.domain.repository.CFRepository
import javax.inject.Inject

class GetUserInfoByHandleUseCase @Inject constructor(
    private val cfRepository: CFRepository
) {
    suspend operator fun invoke(handle: String): Either<AppError, User> {
        return cfRepository.getUserInfoByHandle(handle)
    }
}