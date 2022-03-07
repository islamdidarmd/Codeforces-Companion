package com.codeforcesvisualizer.contest.details

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeforcesvisualizer.core.data.data.Either
import com.codeforcesvisualizer.domain.usecase.GetContestByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContestDetailsViewModel @Inject constructor(
    val getContestByIdUseCase: GetContestByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ContestDetailsUiState())
    val uiState: StateFlow<ContestDetailsUiState> = _uiState

    private val _remainingTimeFlow = MutableStateFlow(0L)
    val remainingTimeFlow: StateFlow<Long> = _remainingTimeFlow

    private var countDownTimer: CountDownTimer? = null

    fun getContestById(id: Int) {
        _uiState.value = _uiState.value.copy(loading = true)

        viewModelScope.launch {
            val data = getContestByIdUseCase(id)
            if (data is Either.Right) {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    userMessage = "",
                    contest = data.data,
                )
                createCountDownTimer(duration = (data.data.startTimeSeconds) - (System.currentTimeMillis() / 1000))
            } else {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    contest = null,
                    userMessage = (data as Either.Left).data.message
                )
            }
        }
    }

    private fun createCountDownTimer(duration: Long) {
        countDownTimer = object : CountDownTimer(duration * 1000, 1000) {
            override fun onTick(remaining: Long) {
                _remainingTimeFlow.value = remaining / 1000
            }

            override fun onFinish() {
                _remainingTimeFlow.value = 0
            }

        }
        countDownTimer?.start()
    }

    override fun onCleared() {
        countDownTimer?.cancel()
        super.onCleared()
    }

}