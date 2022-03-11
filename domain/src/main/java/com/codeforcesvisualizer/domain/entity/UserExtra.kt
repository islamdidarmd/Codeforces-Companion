package com.codeforcesvisualizer.domain.entity

data class UserExtra(
    val contestId: Int,
    val rank: Int,
    val oldRating: Int,
    val newRating: Int
)