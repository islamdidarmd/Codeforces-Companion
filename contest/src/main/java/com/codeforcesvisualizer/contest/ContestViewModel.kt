package com.codeforcesvisualizer.contest

import androidx.lifecycle.ViewModel
import com.codeforcesvisualizer.domain.usecase.GetContestListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class ContestViewModel(
    private val contestListUseCase: GetContestListUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ContestListUiState(loading = true, emptyList(), ""))
    val uiState: Flow<ContestListUiState> = _uiState
}