package com.codeforcesvisualizer.compare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeforcesvisualizer.core.data.Either
import com.codeforcesvisualizer.domain.usecase.GetUserRatingsByHandleUseCase
import com.codeforcesvisualizer.domain.usecase.GetUserStatusByHandleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
            val data1 = getUserRatingsByHandleUseCase(handle1)
            delay(2 * 1000)
            val data2 = getUserRatingsByHandleUseCase(handle2)

            when (data1) {
                is Either.Left -> {
                    _userRatingState.value = _userRatingState.value.copy(
                        loading = false,
                        userMessage = data1.data.message,
                        userRatings1 = null
                    )
                }

                is Either.Right -> {
                    _userRatingState.value = _userRatingState.value.copy(
                        loading = false,
                        userMessage = "",
                        userRatings1 = data1.data
                    )
                }
            }
            when (data2) {
                is Either.Left -> {
                    _userRatingState.value = _userRatingState.value.copy(
                        loading = false,
                        userMessage = data2.data.message,
                        userRatings2 = null
                    )
                }

                is Either.Right -> {
                    _userRatingState.value = _userRatingState.value.copy(
                        loading = false,
                        userMessage = "",
                        userRatings2 = data2.data
                    )
                }
            }
        }
    }

    fun getUserStatusByHandle(handle1: String, handle2: String) {
        _userStatusState.value = _userStatusState.value.copy(loading = true)
        viewModelScope.launch {
            val data1 = getUserStatusByHandleUseCase(handle1)
            delay(2 * 1000)
            val data2 = getUserStatusByHandleUseCase(handle2)

            when (data1) {
                is Either.Left -> {
                    _userStatusState.value = _userStatusState.value.copy(
                        loading = false,
                        userMessage = data1.data.message,
                        userStatus1 = null
                    )
                }

                is Either.Right -> {
                    _userStatusState.value = _userStatusState.value.copy(
                        loading = false,
                        userMessage = "",
                        userStatus1 = data1.data
                    )
                }
            }
            when (data2) {
                is Either.Left -> {
                    _userStatusState.value = _userStatusState.value.copy(
                        loading = false,
                        userMessage = data2.data.message,
                        userStatus2 = null
                    )
                }

                is Either.Right -> {
                    _userStatusState.value = _userStatusState.value.copy(
                        loading = false,
                        userMessage = "",
                        userStatus2 = data2.data
                    )
                }
            }
        }
    }
}