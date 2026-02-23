package com.codeforcesvisualizer.preference

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberRateAppHandler(): RateAppHandler {
    val context = LocalContext.current
    return remember(context) {
        AndroidRateAppHandler(context)
    }
}

private class AndroidRateAppHandler(
    private val context: Context
) : RateAppHandler {

    private val packageName: String = context.packageName

    override val versionName: String = runCatching {
        val packageManager = context.packageManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.getPackageInfo(
                packageName,
                PackageManager.PackageInfoFlags.of(0)
            ).versionName
        } else {
            @Suppress("DEPRECATION")
            packageManager.getPackageInfo(packageName, 0).versionName
        }
    }.getOrNull().orEmpty().ifEmpty { "—" }

    override fun openStore(): Boolean {
        return try {
            launchIntent(Uri.parse("market://details?id=$packageName"))
            true
        } catch (marketException: Exception) {
            try {
                launchIntent(Uri.parse("https://play.google.com/store/apps/details?id=$packageName"))
                true
            } catch (fallbackException: Exception) {
                false
            }
        }
    }

    private fun launchIntent(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}
