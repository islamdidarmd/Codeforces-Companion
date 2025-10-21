package com.codeforcesvisualizer.data.model

import com.codeforcesvisualizer.domain.entity.UserRating
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRatingModel(
    @SerialName("contestId")
    val contestId: Int,

    @SerialName("rank")
    val rank: Int,

    @SerialName("oldRating")
    val oldRating: Int,

    @SerialName("newRating")
    val newRating: Int
) {
    fun toEntity(): UserRating {
        return UserRating(
            contestId = contestId,
            rank = rank,
            oldRating = oldRating,
            newRating = newRating
        )
    }
}