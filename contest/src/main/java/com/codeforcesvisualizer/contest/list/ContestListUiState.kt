package com.codeforcesvisualizer.contest.list

import com.codeforcesvisualizer.domain.entity.Contest

data class ContestListUiState(
    val loading: Boolean = false,
    val contestList: List<Contest> = emptyList(),
    val userMessage: String = "",
)
