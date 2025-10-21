package com.codeforcesvisualizer.shared.data.model

import com.codeforcesvisualizer.shared.domain.entity.Contest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContestModel(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("type")
    val type: Type,
    @SerialName("phase")
    val phase: Phase,
    @SerialName("frozen")
    val frozen: Boolean,
    @SerialName("durationSeconds")
    val durationSeconds: Int,
    @SerialName("startTimeSeconds")
    val startTimeSeconds: Int,
    @SerialName("relativeTimeSeconds")
    val relativeTimeSeconds: Int,
    @SerialName("preparedBy")
    val preparedBy: String? = null,
    @SerialName("websiteUrl")
    val websiteUrl: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("difficulty")
    val difficulty: Int? = null,
    @SerialName("kind")
    val kind: String? = null,
    @SerialName("icpcRegion")
    val icpcRegion: String? = null,
    @SerialName("country")
    val country: String? = null,
    @SerialName("season")
    val season: String? = null,
) {
    fun toEntity(): Contest {
        return Contest(
            id = id,
            name = name,
            type = type.name,
            phase = PhaseMapper.map(phase),
            frozen = frozen,
            durationSeconds = durationSeconds,
            startTimeSeconds = startTimeSeconds,
            relativeTimeSeconds = relativeTimeSeconds,
            scheduled = phase == Phase.BEFORE,
            preparedBy = preparedBy,
            websiteUrl = websiteUrl,
            description = description,
            difficulty = difficulty,
            kind = kind,
            icpcRegion = icpcRegion,
            country = country,
            season = season
        )
    }
}

object PhaseMapper {
    fun map(phase: Phase): String {
        return when (phase) {
            Phase.BEFORE -> "Scheduled"
            Phase.CODING -> "Running"
            Phase.PENDING_SYSTEM_TEST -> "Pending System Test"
            Phase.SYSTEM_TEST -> "Running System Test"
            Phase.FINISHED -> "Finished"
        }
    }
}

@Serializable
enum class Phase {
    BEFORE,
    CODING,
    PENDING_SYSTEM_TEST,
    SYSTEM_TEST,
    FINISHED
}

@Serializable
enum class Type {
    CF,
    IOI,
    ICPC
}