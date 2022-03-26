package com.codeforcesvisualizer.profile

import com.codeforcesvisualizer.domain.entity.UserRating

data class UserRatingUiState(
    val loading: Boolean = false,
    val userMessage: String = "",
    val userRatings: List<UserRating>? = null
)
