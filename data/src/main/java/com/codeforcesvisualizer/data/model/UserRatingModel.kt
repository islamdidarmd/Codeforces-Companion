package com.codeforcesvisualizer.data.model

import com.codeforcesvisualizer.domain.entity.UserRating
import com.squareup.moshi.Json

data class UserRatingModel(
    @field:Json(name = "contestId")
    val contestId: Int,

    @field:Json(name = "rank")
    val rank: Int,

    @field:Json(name = "oldRating")
    val oldRating: Int,

    @field:Json(name = "newRating")
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