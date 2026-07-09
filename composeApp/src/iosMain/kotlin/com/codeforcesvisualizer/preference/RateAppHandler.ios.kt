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
                val appId = "6775346654"
                // Try to launch App Store app directly
                val appStoreUrl = NSURL.URLWithString("itms-apps://itunes.apple.com/app/id$appId")
                if (appStoreUrl != null && UIApplication.sharedApplication.openURL(appStoreUrl)) {
                    return true
                }
                // Fallback to standard web preview URL
                val webUrl = NSURL.URLWithString("https://apps.apple.com/app/id$appId")
                if (webUrl != null && UIApplication.sharedApplication.openURL(webUrl)) {
                    return true
                }
                // Fallback: return true to avoid showing "store not found" error on simulators
                return true
            }
        }
    }
}
