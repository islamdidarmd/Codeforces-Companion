package com.codeforcesvisualizer.compare

import com.codeforcesvisualizer.domain.entity.UserRating

data class UserRatingUiState(
    val loading: Boolean = false,
    val userMessage: String = "",
    val userRatings1: List<UserRating>? = null,
    val userRatings2: List<UserRating>? = null
)