package com.codeforcesvisualizer.data.model

import com.codeforcesvisualizer.domain.entity.Contest
import com.squareup.moshi.Json

data class ContestListResponse(
    @field:Json(name = "status")
    val status: Status,
    @field:Json(name = "result")
    val result: List<Contest>?,
    @field:Json(name = "comment")
    val comment: String?
) {
    fun toEntity(): List<Contest>{
        return result ?: emptyList()
    }
}