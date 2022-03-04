package com.codeforcesvisualizer.data.model

import com.codeforcesvisualizer.domain.entity.Contest
import com.squareup.moshi.Json

data class ContestListResponseModel(
    @field:Json(name = "status")
    val statusModel: StatusModel,
    @field:Json(name = "result")
    val result: List<ContestModel>?,
    @field:Json(name = "comment")
    val comment: String?
) {
    fun toEntity(): List<Contest> {
        return result?.map { it.toEntity() } ?: emptyList()
    }
}