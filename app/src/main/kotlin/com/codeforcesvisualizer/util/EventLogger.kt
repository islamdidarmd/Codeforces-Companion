package com.codeforcesvisualizer.util

import com.codeforcesvisualizer.BuildConfig

object EventLogger {
    fun logEvent(event: String) {
        if (BuildConfig.DEBUG) return
    //    Answers.getInstance().logCustom(CustomEvent(event))
    }
}