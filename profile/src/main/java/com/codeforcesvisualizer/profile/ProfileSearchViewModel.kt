package com.codeforcesvisualizer.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeforcesvisualizer.core.data.data.Either
import com.codeforcesvisualizer.domain.usecase.GetUserInfoByHandleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileSearchViewModel @Inject constructor(
    private val getUserInfoByHandleUseCase: GetUserInfoByHandleUseCase
) : ViewModel() {
    private val _searchTextState = MutableStateFlow("")
    val searchTextState: StateFlow<String> = _searchTextState

    private val _userInfoState = MutableStateFlow(UserInfoUiState())
    val userInfoState: StateFlow<UserInfoUiState> = _userInfoState

    fun onSearchTextChanged(text: String) {
        _searchTextState.value = text
    }

    fun getUserInfoByHandle(handle: String) {
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
}