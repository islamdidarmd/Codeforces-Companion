package com.codeforcesvisualizer.contest

import com.codeforcesvisualizer.domain.entity.Contest

data class ContestListUiState(
    val loading: Boolean = true,
    val contestList: List<Contest> = emptyList(),
    val userMessage: String = "",
)
