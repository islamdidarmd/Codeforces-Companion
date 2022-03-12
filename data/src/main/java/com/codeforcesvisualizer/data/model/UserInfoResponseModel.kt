package com.codeforcesvisualizer.data.model

import com.codeforcesvisualizer.domain.entity.User
import com.squareup.moshi.Json

data class UserInfoResponseModel(
    @field:Json(name = "status")
    val statusModel: StatusModel,

    @field:Json(name = "result")
    val result: List<UserInfoModel>?,

    @field:Json(name = "comment")
    val comment: String?
) {
    fun toEntity(): List<User> {
        return result?.map { it.toEntity() } ?: emptyList()
    }
}