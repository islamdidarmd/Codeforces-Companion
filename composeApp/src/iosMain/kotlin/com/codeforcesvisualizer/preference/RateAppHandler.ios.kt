package com.codeforcesvisualizer.preference

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSBundle

@Composable
actual fun rememberRateAppHandler(): RateAppHandler {
    return remember {
        object : RateAppHandler {
            override val versionName: String = runCatching {
                NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as? String
            }.getOrNull().orEmpty().ifEmpty { "—" }

            override fun openStore(): Boolean = false
        }
    }
}
