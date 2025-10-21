package com.codeforcesvisualizer.shared.data.model

import com.codeforcesvisualizer.shared.domain.entity.UserRating
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRatingResponseModel(
    @SerialName("status")
    override val statusModel: StatusModel,

    @SerialName("result")
    val result: List<UserRatingModel>? = null,

    @SerialName("comment")
    override val comment: String? = null
) : BaseResponseModel {
    fun toEntity(): List<UserRating> {
        return result?.map { it.toEntity() } ?: emptyList()
    }
}