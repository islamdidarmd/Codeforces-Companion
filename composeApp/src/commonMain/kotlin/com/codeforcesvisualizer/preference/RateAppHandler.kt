package com.codeforcesvisualizer.preference

import androidx.compose.runtime.Composable

interface RateAppHandler {
    val versionName: String
    fun openStore(): Boolean
}

@Composable
expect fun rememberRateAppHandler(): RateAppHandler
