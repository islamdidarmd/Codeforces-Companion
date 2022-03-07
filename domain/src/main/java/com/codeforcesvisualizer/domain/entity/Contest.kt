package com.codeforcesvisualizer.domain.entity

data class Contest(
    val id: Int,
    val name: String,
    val type: String,
    val phase: String,
    val frozen: Boolean,
    val scheduled: Boolean,
    val durationSeconds: Int,
    val startTimeSeconds: Int,
    val relativeTimeSeconds: Int,
    val preparedBy: String?,
    val websiteUrl: String?,
    val description: String?,
    val difficulty: Int?,
    val kind: String?,
    val icpcRegion: String?,
    val country: String?,
    val season: String?,
)