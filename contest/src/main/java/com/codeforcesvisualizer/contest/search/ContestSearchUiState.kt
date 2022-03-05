package com.codeforcesvisualizer.contest.search

import com.codeforcesvisualizer.domain.entity.Contest

data class ContestSearchUiState(
    val loading: Boolean = false,
    val userMessage: String = "",
    val matches: List<Contest> = emptyList()
)