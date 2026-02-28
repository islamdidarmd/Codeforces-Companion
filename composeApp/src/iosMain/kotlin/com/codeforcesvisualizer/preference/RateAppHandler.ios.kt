package com.codeforcesvisualizer.preference

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSBundle
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

@Composable
actual fun rememberRateAppHandler(): RateAppHandler {
    return remember {
        object : RateAppHandler {
            override val versionName: String = runCatching {
                NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as? String
            }.getOrNull().orEmpty().ifEmpty { "—" }

            override fun openStore(): Boolean {
                val bundleId = NSBundle.mainBundle.bundleIdentifier ?: return false
                val url = NSURL.URLWithString("https://apps.apple.com/app/id$bundleId")
                    ?: return false
                return UIApplication.sharedApplication.openURL(url)
            }
        }
    }
}
