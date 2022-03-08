package com.codeforcesvisualizer.webview

import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebSettings.LOAD_DEFAULT
import android.webkit.WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.codeforcesvisualizer.core.data.components.CFAppBar
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CFWebViewScreen(
    modifier: Modifier = Modifier,
    link: String,
    onNavigateBack: () -> Unit,
) {
    val state = rememberWebViewState(url = link)
    val title  = "Codeforces"

    Scaffold(
        modifier = modifier,
        topBar = {
            CFAppBar(
                title = title,
                onNavigateBack = onNavigateBack
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            WebView(
                state = state,
                onCreated = { webview ->
                    setWebViewSettings(webview.settings)
                }
            )
            if (state.isLoading)
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}

private fun setWebViewSettings(settings: WebSettings) {
    settings.apply {
        setSupportZoom(true)
        builtInZoomControls = true
        displayZoomControls = false
        useWideViewPort = true
        allowFileAccess = true
        allowContentAccess = true
        setSupportMultipleWindows(false)
        databaseEnabled = true
        domStorageEnabled = true
        javaScriptCanOpenWindowsAutomatically = false
        cacheMode = LOAD_DEFAULT
        mixedContentMode = MIXED_CONTENT_COMPATIBILITY_MODE
        javaScriptEnabled = true
    }
}