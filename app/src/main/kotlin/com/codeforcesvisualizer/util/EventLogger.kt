package com.codeforcesvisualizer.util

import com.codeforcesvisualizer.BuildConfig
import com.crashlytics.android.answers.Answers
import com.crashlytics.android.answers.CustomEvent

object EventLogger {
    fun logEvent(event: String) {
        if (BuildConfig.DEBUG) return
        Answers.getInstance().logCustom(CustomEvent(event))
    }
}