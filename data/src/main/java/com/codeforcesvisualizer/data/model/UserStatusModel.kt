package com.codeforcesvisualizer.data.model

import com.codeforcesvisualizer.domain.entity.UserStatus
import com.squareup.moshi.Json

data class UserStatusModel(
    @field:Json(name = "programmingLanguage")
    val programmingLanguage: String,

    @field:Json(name = "verdict")
    val verdict: String,

    @field:Json(name = "problem")
    val problemModel: ProblemModel
) {
    fun toEntity(): UserStatus {
        return UserStatus(
            programmingLanguage = programmingLanguage,
            verdict = verdict,
            problem = problemModel.toEntity()
        )
    }
}