package com.codeforcesvisualizer.domain.usecase

import com.codeforcesvisualizer.core.data.data.AppError
import com.codeforcesvisualizer.core.data.data.Either
import com.codeforcesvisualizer.domain.entity.Contest
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