package com.codeforcesvisualizer.contest.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeforcesvisualizer.shared.core.Either
import com.codeforcesvisualizer.shared.domain.usecase.GetContestByIdUseCase
import kotlinx.datetime.Clock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContestDetailsViewModel(
    val getContestByIdUseCase: GetContestByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ContestDetailsUiState())
    val uiState: StateFlow<ContestDetailsUiState> = _uiState

    private val _remainingTimeFlow = MutableStateFlow(0L)
    val remainingTimeFlow: StateFlow<Long> = _remainingTimeFlow

    private var countdownJob: Job? = null

    fun getContestById(id: Int) {
        _uiState.value = _uiState.value.copy(loading = true)

        viewModelScope.launch {
            val data = getContestByIdUseCase(id)
            if (data is Either.Right) {
                val secondsUntilStart = data.data.startTimeSeconds.toLong() - Clock.System.now().epochSeconds
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    userMessage = "",
                    contest = data.data,
                )
                startCountdown(duration = secondsUntilStart)
            } else {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    contest = null,
                    userMessage = (data as Either.Left).data.message
                )
            }
        }
    }

    private fun startCountdown(duration: Long) {
        countdownJob?.cancel()
        val initialDuration = duration.coerceAtLeast(0L)
        if (initialDuration == 0L) {
            _remainingTimeFlow.value = 0
            return
        }

        countdownJob = viewModelScope.launch(Dispatchers.Default) {
            var remainingSeconds = initialDuration
            while (remainingSeconds >= 0 && isActive) {
                _remainingTimeFlow.value = remainingSeconds
                if (remainingSeconds == 0L) break
                delay(1000)
                remainingSeconds--
            }
        }
    }

    override fun onCleared() {
        countdownJob?.cancel()
        super.onCleared()
    }

}