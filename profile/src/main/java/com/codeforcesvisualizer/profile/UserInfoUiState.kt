package com.codeforcesvisualizer.profile

import com.codeforcesvisualizer.domain.entity.User

data class UserInfoUiState(
    val loading: Boolean = false,
    val userMessage: String = "",
    val user: User? = null
)
