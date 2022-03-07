package com.codeforcesvisualizer.data.model

import com.codeforcesvisualizer.domain.entity.Contest
import com.squareup.moshi.Json

data class ContestModel(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "type")
    val type: Type,
    @field:Json(name = "phase")
    val phase: Phase,
    @field:Json(name = "frozen")
    val frozen: Boolean,
    @field:Json(name = "durationSeconds")
    val durationSeconds: Int,
    @field:Json(name = "startTimeSeconds")
    val startTimeSeconds: Int,
    @field:Json(name = "relativeTimeSeconds")
    val relativeTimeSeconds: Int,
    @field:Json(name = "preparedBy")
    val preparedBy: String?,
    @field:Json(name = "websiteUrl")
    val websiteUrl: String?,
    @field:Json(name = "description")
    val description: String?,
    @field:Json(name = "difficulty")
    val difficulty: Int?,
    @field:Json(name = "kind")
    val kind: String?,
    @field:Json(name = "icpcRegion")
    val icpcRegion: String?,
    @field:Json(name = "country")
    val country: String?,
    @field:Json(name = "season")
    val season: String?,
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

enum class Phase {
    BEFORE,
    CODING,
    PENDING_SYSTEM_TEST,
    SYSTEM_TEST,
    FINISHED
}

enum class Type {
    CF,
    IOI,
    ICPC
}