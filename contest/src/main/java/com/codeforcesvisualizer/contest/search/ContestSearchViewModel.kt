package com.codeforcesvisualizer.contest.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeforcesvisualizer.core.data.data.AppError
import com.codeforcesvisualizer.core.data.data.Either
import com.codeforcesvisualizer.domain.entity.Contest
import com.codeforcesvisualizer.domain.usecase.FilterContestListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContestSearchViewModel @Inject constructor(
    private val filterContestListUseCase: FilterContestListUseCase
) : ViewModel() {
    private val _searchTextFlow = MutableStateFlow("")
    val searchTextFlow: Flow<String> = _searchTextFlow

    private val _uiState = MutableStateFlow(ContestSearchUiState())
    val uiState: Flow<ContestSearchUiState> = _uiState

    fun onSearchTextChanged(text: String) {
        _searchTextFlow.value = text
        _uiState.value = _uiState.value.copy(loading = true)

        viewModelScope.launch {
            val data: Either<AppError, List<Contest>> = filterContestListUseCase.invoke(text)
            if (data is Either.Right) {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    userMessage = "",
                    matches = data.data,
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    matches = emptyList(),
                    userMessage = (data as Either.Left).data.message
                )
            }
        }
    }
}