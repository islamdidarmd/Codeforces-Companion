package com.codeforcesvisualizer.core

object EventLogger {
    //private lateinit var logger: (String, Bundle) -> Unit

    fun initialize(onLogEvent: (String, Any) -> Unit) {
        //this.logger = onLogEvent
    }

    fun logEvent(event: String, param: Any = Any()) {
       /* if (!this::logger.isInitialized) return
        logger.invoke(
            event.replace(" ", "_").replace("-", "_"),
            param
        )*/
    }

    fun logScreenView(screen: String, param: Any = Any()) {
        /*param.putString("Screen", screen)
        logEvent(event = FirebaseAnalytics.Event.SCREEN_VIEW, param = param)*/
    }
}