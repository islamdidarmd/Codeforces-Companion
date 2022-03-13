package com.codeforcesvisualizer.domain.usecase

import com.codeforcesvisualizer.core.data.data.AppError
import com.codeforcesvisualizer.core.data.data.Either
import com.codeforcesvisualizer.domain.entity.Contest
import com.codeforcesvisualizer.domain.entity.User
import com.codeforcesvisualizer.domain.entity.UserRating
import com.codeforcesvisualizer.domain.entity.UserStatus
import com.codeforcesvisualizer.domain.repository.CFRepository
import javax.inject.Inject

class GetUserRatingsByHandleUseCase @Inject constructor(
    private val cfRepository: CFRepository
) {
    suspend operator fun invoke(handle: String): Either<AppError, List<UserRating>> {
        return cfRepository.getUserRatingByHandle(handle)
    }
}