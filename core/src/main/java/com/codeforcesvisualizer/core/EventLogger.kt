package com.codeforcesvisualizer.core

import android.os.Bundle
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics

object EventLogger {
    private lateinit var logger: (String, Bundle) -> Unit

    fun initialize(onLogEvent: (String, Bundle) -> Unit) {
        this.logger = onLogEvent
    }

    fun logEvent(event: String, param: Bundle = bundleOf()) {
        if (!this::logger.isInitialized) return
        logger.invoke(
            event.replace(" ", "_").replace("-", "_"),
            param
        )
    }

    fun logScreenView(screen: String, param: Bundle = bundleOf()) {
        param.putString("Screen", screen)
        logEvent(event = FirebaseAnalytics.Event.SCREEN_VIEW, param = param)
    }
}