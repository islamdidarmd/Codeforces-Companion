package com.codeforcesvisualizer.profile

import com.codeforcesvisualizer.shared.domain.entity.UserStatus

data class UserStatusUiState(
    val loading: Boolean = false,
    val userMessage: String = "",
    val userStatus: List<UserStatus>? = null
)
