package com.codeforcesvisualizer.shared.data.model

import com.codeforcesvisualizer.shared.domain.entity.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponseModel(
    @SerialName("status")
    override val statusModel: StatusModel,

    @SerialName("result")
    val result: List<UserInfoModel>? = null,

    @SerialName("comment")
    override val comment: String? = null
) : BaseResponseModel {
    fun toEntity(): List<User> {
        return result?.map { it.toEntity() } ?: emptyList()
    }
}