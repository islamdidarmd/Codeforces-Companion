package com.codeforcesvisualizer.data.model

import com.codeforcesvisualizer.domain.entity.UserStatus
import com.squareup.moshi.Json

data class UserStatusResponseModel(
    @field:Json(name = "status")
    val statusModel: StatusModel,

    @field:Json(name = "result")
    val result: List<UserStatusModel>?,

    @field:Json(name = "comment")
    val comment: String?
) {
    fun toEntity(): List<UserStatus> {
        return result?.map { it.toEntity() } ?: emptyList()
    }
}