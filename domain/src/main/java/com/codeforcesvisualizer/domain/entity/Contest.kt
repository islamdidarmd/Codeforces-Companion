package com.codeforcesvisualizer.domain.entity

data class Contest(
    val id: Int,
    val name: String,
    val type: Type,
    val phase: Phase,
    val frozen: Boolean,
    val durationSeconds: Int,
    val startTimeSeconds: Long,
    val relativeTimeSeconds: Long,
    val preparedBy: String?,
    val websiteUrl: String?,
    val description: String?,
    val difficulty: Int?,
    val kind: String?,
    val icpcRegion: String?,
    val country: String?,
    val season: String?,
)
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