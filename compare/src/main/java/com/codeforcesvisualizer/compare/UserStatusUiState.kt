package com.codeforcesvisualizer.compare

import com.codeforcesvisualizer.domain.entity.UserStatus

data class UserStatusUiState(
    val loading: Boolean = false,
    val userMessage: String = "",
    val userStatus1: List<UserStatus>? = null,
    val userStatus2: List<UserStatus>? = null
)