package com.codeforcesvisualizer.preference

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeforcesvisualizer.shared.domain.entity.UiThemeMode
import com.codeforcesvisualizer.shared.domain.usecase.GetUiThemeModeUseCase
import com.codeforcesvisualizer.shared.domain.usecase.SetUiThemeModeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ThemeManagerViewModel(
    private val getUiThemeModeUseCase: GetUiThemeModeUseCase,
    private val setUiThemeModeUseCase: SetUiThemeModeUseCase
) : ViewModel(), ThemeManager {
    private val _themeModeFlow = MutableStateFlow(ThemeModeUiState())
    override val themeModeFlow: StateFlow<ThemeModeUiState> = _themeModeFlow

    init {
        getUiThemeMode()
    }

    private fun getUiThemeMode() {
        viewModelScope.launch(Dispatchers.IO) {
            _themeModeFlow.value = _themeModeFlow.value.copy(themeMode = getUiThemeModeUseCase())
        }
    }

    override fun setUiThemeMode(themeMode: UiThemeMode) {
        if (_themeModeFlow.value.themeMode == themeMode) return
        _themeModeFlow.value = _themeModeFlow.value.copy(themeMode = themeMode)
        viewModelScope.launch(Dispatchers.IO) {
            setUiThemeModeUseCase(themeMode)
        }
    }
}