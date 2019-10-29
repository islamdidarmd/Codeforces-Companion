package com.codeforcesvisualizer.model

data class Contest(val id: Int = 0,
              val name: String = "",
              val type: String = "",
              val phase: PHASE? = null,
              val frozen: Boolean = false,
              val durationSeconds: Int = 0,
              val startTimeSeconds: Long = 0,
              val relativeTimeSeconds: Long = 0) {

    enum class PHASE {
        BEFORE,
        FINISHED
    }
}