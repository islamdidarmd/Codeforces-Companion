package com.codeforcesvisualizer.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeforcesvisualizer.core.data.RecentSearchRepository
import com.codeforcesvisualizer.shared.core.Either
import com.codeforcesvisualizer.shared.domain.usecase.GetUserInfoByHandleUseCase
import com.codeforcesvisualizer.shared.domain.usecase.GetUserRatingsByHandleUseCase
import com.codeforcesvisualizer.shared.domain.usecase.GetUserStatusByHandleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileSearchViewModel(
    private val getUserInfoByHandleUseCase: GetUserInfoByHandleUseCase,
    private val getUserStatusByHandleUseCase: GetUserStatusByHandleUseCase,
    private val getUserRatingByHandleUseCase: GetUserRatingsByHandleUseCase,
    private val recentSearchRepository: RecentSearchRepository
) : ViewModel() {
    private val _searchTextState = MutableStateFlow("")
    val searchTextState: StateFlow<String> = _searchTextState

    private val _userInfoState = MutableStateFlow(UserInfoUiState())
    val userInfoState: StateFlow<UserInfoUiState> = _userInfoState

    private val _userStatusState = MutableStateFlow(UserStatusUiState())
    val userStatusState: StateFlow<UserStatusUiState> = _userStatusState

    private val _userRatingState = MutableStateFlow(UserRatingUiState())
    val userRatingState: StateFlow<UserRatingUiState> = _userRatingState

    val recentSearches: StateFlow<List<String>> = recentSearchRepository.recentSearches
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun onSearchTextChanged(text: String) {
        _searchTextState.value = text
    }

    fun getUserInfoByHandle(handle: String) {
        viewModelScope.launch { recentSearchRepository.addSearch(handle) }
        _userInfoState.value = _userInfoState.value.copy(loading = true)
        viewModelScope.launch {
            when (val data = getUserInfoByHandleUseCase(handle)) {
                is Either.Left -> {
                    _userInfoState.value = _userInfoState.value.copy(
                        loading = false,
                        userMessage = data.data.message,
                        user = null
                    )
                }

                is Either.Right -> {
                    _userInfoState.value = _userInfoState.value.copy(
                        loading = false,
                        userMessage = "",
                        user = data.data
                    )
                }
            }
        }
    }

    fun getUserStatusByHandle(handle: String) {
        _userStatusState.value = _userStatusState.value.copy(loading = true)
        viewModelScope.launch {
            when (val data = getUserStatusByHandleUseCase(handle)) {
                is Either.Left -> {
                    _userStatusState.value = _userStatusState.value.copy(
                        loading = false,
                        userMessage = data.data.message,
                        userStatus = null
                    )
                }

                is Either.Right -> {
                    _userStatusState.value = _userStatusState.value.copy(
                        loading = false,
                        userMessage = "",
                        userStatus = data.data
                    )
                }
            }
        }
    }

    fun getUserRatingByHandle(handle: String) {
        _userRatingState.value = _userRatingState.value.copy(loading = true)
        viewModelScope.launch {
            when (val data = getUserRatingByHandleUseCase(handle)) {
                is Either.Left -> {
                    _userRatingState.value = _userRatingState.value.copy(
                        loading = false,
                        userMessage = data.data.message,
                        userRatings = null
                    )
                }

                is Either.Right -> {
                    _userRatingState.value = _userRatingState.value.copy(
                        loading = false,
                        userMessage = "",
                        userRatings = data.data
                    )
                }
            }
        }
    }

    fun clearRecentSearches() {
        viewModelScope.launch { recentSearchRepository.clearAll() }
    }
}