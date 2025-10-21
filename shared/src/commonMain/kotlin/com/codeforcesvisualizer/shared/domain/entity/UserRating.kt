package com.codeforcesvisualizer.shared.domain.entity

data class UserRating(
    val contestId: Int,
    val rank: Int,
    val oldRating: Int,
    val newRating: Int
)