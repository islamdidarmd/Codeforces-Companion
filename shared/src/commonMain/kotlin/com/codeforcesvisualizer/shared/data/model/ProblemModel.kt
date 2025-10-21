package com.codeforcesvisualizer.shared.data.model

import com.codeforcesvisualizer.domain.entity.Problem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProblemModel(
    @SerialName("contestId")
    val contestId: Int,

    @SerialName("index")
    val index: String,

    @SerialName("tags")
    val tags: List<String> = emptyList(),

    @SerialName("name")
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