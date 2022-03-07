package com.codeforcesvisualizer.contest.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeforcesvisualizer.core.data.data.AppError
import com.codeforcesvisualizer.core.data.data.Either
import com.codeforcesvisualizer.domain.entity.Contest
import com.codeforcesvisualizer.domain.usecase.GetContestListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContestViewModel @Inject constructor(
    private val contestListUseCase: GetContestListUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ContestListUiState())
    val uiState: StateFlow<ContestListUiState> = _uiState

    init {
        loadContestList()
    }


    private fun loadContestList() {
        _uiState.value = _uiState.value.copy(loading = true)
        viewModelScope.launch {
            val data: Either<AppError, List<Contest>> = contestListUseCase.invoke()
            if (data is Either.Right) {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    userMessage = "",
                    contestList = data.data,
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    contestList = emptyList(),
                    userMessage = (data as Either.Left).data.message
                )
            }
        }
    }
}