package com.codeforcesvisualizer.shared.data.model

import com.codeforcesvisualizer.shared.domain.entity.Contest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContestListResponseModel(
    @SerialName("status")
    override val statusModel: StatusModel,
    @SerialName("result")
    val result: List<ContestModel>? = null,
    @SerialName("comment")
    override val comment: String? = null
) : BaseResponseModel {
    fun toEntity(): List<Contest> {
        return result?.map { it.toEntity() } ?: emptyList()
    }
}