package com.codeforcesvisualizer.compare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeforcesvisualizer.core.data.data.Either
import com.codeforcesvisualizer.domain.usecase.GetUserRatingsByHandleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompareHandlesViewModel @Inject constructor(
    private val getUserRatingsByHandleUseCase: GetUserRatingsByHandleUseCase
) : ViewModel() {
    private val _userRatingState = MutableStateFlow(UserRatingUiState())
    val userRatingState: StateFlow<UserRatingUiState> = _userRatingState

    fun getUserRatingByHandle(handle1: String, handle2: String) {
        _userRatingState.value = _userRatingState.value.copy(loading = true)
        viewModelScope.launch {
            when (val data = getUserRatingsByHandleUseCase(handle1)) {
                is Either.Left -> {
                    _userRatingState.value = _userRatingState.value.copy(
                        loading = false,
                        userMessage = data.data.message,
                        userRatings1 = null
                    )
                }

                is Either.Right -> {
                    _userRatingState.value = _userRatingState.value.copy(
                        loading = false,
                        userMessage = "",
                        userRatings1 = data.data
                    )
                }
            }
            when (val data = getUserRatingsByHandleUseCase(handle2)) {
                is Either.Left -> {
                    _userRatingState.value = _userRatingState.value.copy(
                        loading = false,
                        userMessage = data.data.message,
                        userRatings2 = null
                    )
                }

                is Either.Right -> {
                    _userRatingState.value = _userRatingState.value.copy(
                        loading = false,
                        userMessage = "",
                        userRatings2 = data.data
                    )
                }
            }
        }
    }
}