package com.codeforcesvisualizer.contest.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeforcesvisualizer.shared.core.AppError
import com.codeforcesvisualizer.shared.core.Either
import com.codeforcesvisualizer.shared.domain.entity.Contest
import com.codeforcesvisualizer.shared.domain.usecase.FilterContestListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContestSearchViewModel(
    private val filterContestListUseCase: FilterContestListUseCase
) : ViewModel() {
    private val _searchTextFlow = MutableStateFlow("")
    val searchTextFlow: StateFlow<String> = _searchTextFlow

    private val _uiState = MutableStateFlow(ContestSearchUiState())
    val uiState: StateFlow<ContestSearchUiState> = _uiState

    fun onSearchTextChanged(text: String) {
        _searchTextFlow.value = text

        viewModelScope.launch {
            val data: Either<AppError, List<Contest>> = filterContestListUseCase.invoke(text)
            if (data is Either.Right) {
                _uiState.value = _uiState.value.copy(
                    userMessage = "",
                    matches = data.data,
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    matches = emptyList(),
                    userMessage = (data as Either.Left).data.message
                )
            }
        }
    }
}