package com.codeforcesvisualizer.domain.usecase

import android.content.SharedPreferences
import com.codeforcesvisualizer.core.data.data.AppError
import com.codeforcesvisualizer.core.data.data.Either
import com.codeforcesvisualizer.domain.entity.Contest
import com.codeforcesvisualizer.domain.entity.UiThemeMode
import com.codeforcesvisualizer.domain.repository.CFRepository
import com.codeforcesvisualizer.domain.repository.ThemeRepository
import javax.inject.Inject

class GetUiThemeModeUseCase @Inject constructor(
    private val themeRepository: ThemeRepository,
    private val sharedPreferences: SharedPreferences,
) {
    operator fun invoke(): UiThemeMode {
        return themeRepository.getUiThemeMode(sharedPreferences)
    }
}