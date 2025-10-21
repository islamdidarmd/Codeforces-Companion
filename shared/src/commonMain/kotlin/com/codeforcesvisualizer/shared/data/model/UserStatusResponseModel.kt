package com.codeforcesvisualizer.shared.data.model

import com.codeforcesvisualizer.shared.domain.entity.UserStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserStatusResponseModel(
    @SerialName("status")
    override val statusModel: StatusModel,

    @SerialName("result")
    val result: List<UserStatusModel>? = null,

    @SerialName("comment")
    override val comment: String? = null
) : BaseResponseModel {
    fun toEntity(): List<UserStatus> {
        return result?.map { it.toEntity() } ?: emptyList()
    }
}