package com.codeforcesvisualizer.shared.domain.entity

data class Problem(
    val contestId: Int,
    val index: String,
    val tags: List<String>,
    val name: String
)