package com.codeforcesvisualizer.preference

import androidx.lifecycle.ViewModel
import com.codeforcesvisualizer.domain.entity.UiThemeMode
import com.codeforcesvisualizer.domain.usecase.GetUiThemeModeUseCase
import com.codeforcesvisualizer.domain.usecase.SetUiThemeModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ThemeManagerViewModel @Inject constructor(
    private val getUiThemeModeUseCase: GetUiThemeModeUseCase,
    private val setUiThemeModeUseCase: SetUiThemeModeUseCase
) : ViewModel() {
    private val _themeModeFlow = MutableStateFlow(ThemeModeUiState())
    val themeModeFlow: StateFlow<ThemeModeUiState> = _themeModeFlow

    init {
        getUiThemeMode()
    }

    private fun getUiThemeMode() {
        _themeModeFlow.value = _themeModeFlow.value.copy(themeMode = getUiThemeModeUseCase())
    }

    fun setUiThemeMode(themeMode: UiThemeMode) {
        _themeModeFlow.value = _themeModeFlow.value.copy(themeMode = themeMode)
        setUiThemeModeUseCase(themeMode)
    }
}