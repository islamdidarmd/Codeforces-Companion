package com.codeforcesvisualizer.contest.details

import com.codeforcesvisualizer.domain.entity.Contest

data class ContestDetailsUiState(
    val loading: Boolean = false,
    val contest: Contest? = null,
    val userMessage: String = ""
)
