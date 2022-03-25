package com.codeforcesvisualizer.domain.entity

sealed class UiThemeMode {
    object Light : UiThemeMode()
    object Dark : UiThemeMode()
    object System : UiThemeMode()
}