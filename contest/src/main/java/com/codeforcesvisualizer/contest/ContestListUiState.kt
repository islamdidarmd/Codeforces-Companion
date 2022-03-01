package com.codeforcesvisualizer.contest

import com.codeforcesvisualizer.domain.entity.Contest

data class ContestListUiState(
    val loading: Boolean,
    val contestList: List<Contest>,
    val userMessage: String,
)
