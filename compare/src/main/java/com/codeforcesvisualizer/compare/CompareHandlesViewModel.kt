package com.codeforcesvisualizer.compare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeforcesvisualizer.core.data.data.Either
import com.codeforcesvisualizer.domain.usecase.GetUserRatingsByHandleUseCase
import com.codeforcesvisualizer.domain.usecase.GetUserStatusByHandleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompareHandlesViewModel @Inject constructor(
    private val getUserRatingsByHandleUseCase: GetUserRatingsByHandleUseCase,
    private val getUserStatusByHandleUseCase: GetUserStatusByHandleUseCase
) : ViewModel() {
    private val _userRatingState = MutableStateFlow(UserRatingUiState())
    val userRatingState: StateFlow<UserRatingUiState> = _userRatingState

    private val _userStatusState = MutableStateFlow(UserStatusUiState())
    val userStatusState: StateFlow<UserStatusUiState> = _userStatusState

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

    fun getUserStatusByHandle(handle1: String, handle2: String) {
        _userStatusState.value = _userStatusState.value.copy(loading = true)
        viewModelScope.launch {
            when (val data = getUserStatusByHandleUseCase(handle1)) {
                is Either.Left -> {
                    _userStatusState.value = _userStatusState.value.copy(
                        loading = false,
                        userMessage = data.data.message,
                        userStatus1 = null
                    )
                }

                is Either.Right -> {
                    _userStatusState.value = _userStatusState.value.copy(
                        loading = false,
                        userMessage = "",
                        userStatus1 = data.data
                    )
                }
            }
            when (val data = getUserStatusByHandleUseCase(handle2)) {
                is Either.Left -> {
                    _userStatusState.value = _userStatusState.value.copy(
                        loading = false,
                        userMessage = data.data.message,
                        userStatus2 = null
                    )
                }

                is Either.Right -> {
                    _userStatusState.value = _userStatusState.value.copy(
                        loading = false,
                        userMessage = "",
                        userStatus2 = data.data
                    )
                }
            }
        }
    }
}