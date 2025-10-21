package com.codeforcesvisualizer.data.model

import com.codeforcesvisualizer.domain.entity.UserStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserStatusModel(
    @SerialName("programmingLanguage")
    val programmingLanguage: String,

    @SerialName("verdict")
    val verdict: String,

    @SerialName("problem")
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