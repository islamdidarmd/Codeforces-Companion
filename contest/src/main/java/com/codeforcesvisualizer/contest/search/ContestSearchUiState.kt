package com.codeforcesvisualizer.contest.search

import com.codeforcesvisualizer.domain.entity.Contest

data class ContestSearchUiState(
    val userMessage: String = "",
    val matches: List<Contest> = emptyList()
)