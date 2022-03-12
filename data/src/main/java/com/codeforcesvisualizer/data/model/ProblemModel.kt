package com.codeforcesvisualizer.data.model

import com.codeforcesvisualizer.domain.entity.Problem
import com.squareup.moshi.Json

data class ProblemModel(
    @field:Json(name = "contestId")
    val contestId: Int,

    @field:Json(name = "index")
    val index: String,

    @field:Json(name = "tags")
    val tags: List<String>,

    @field:Json(name = "name")
    val name: String
) {
    fun toEntity(): Problem {
        return Problem(
            contestId = contestId,
            index = index,
            tags = tags,
            name = name
        )
    }
}