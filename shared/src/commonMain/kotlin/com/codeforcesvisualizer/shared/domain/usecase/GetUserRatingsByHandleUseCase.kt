package com.codeforcesvisualizer.shared.domain.usecase

import com.codeforcesvisualizer.core.data.AppError
import com.codeforcesvisualizer.core.data.Either
import com.codeforcesvisualizer.shared.domain.entity.UserRating
import com.codeforcesvisualizer.shared.domain.repository.CFRepository

class GetUserRatingsByHandleUseCase(
    private val cfRepository: CFRepository
) {
    suspend operator fun invoke(handle: String): Either<AppError, List<UserRating>> {
        return cfRepository.getUserRatingByHandle(handle)
    }
}