package com.codeforcesvisualizer.contest

import androidx.lifecycle.ViewModel
import com.codeforcesvisualizer.domain.usecase.GetContestListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ContestViewModel @Inject constructor(
    private val contestListUseCase: GetContestListUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ContestListUiState(loading = true, emptyList(), ""))
    val uiState: Flow<ContestListUiState> = _uiState
}