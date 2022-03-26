package com.codeforcesvisualizer.data.model

import com.codeforcesvisualizer.domain.entity.UserRating
import com.squareup.moshi.Json

data class UserRatingResponseModel(
    @field:Json(name = "status")
    val statusModel: StatusModel,

    @field:Json(name = "result")
    val result: List<UserRatingModel>?,

    @field:Json(name = "comment")
    val comment: String?
) {
    fun toEntity(): List<UserRating> {
        return result?.map { it.toEntity() } ?: emptyList()
    }
}