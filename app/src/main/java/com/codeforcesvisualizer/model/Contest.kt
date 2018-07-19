package com.codeforcesvisualizer.model

import com.google.gson.Gson

class Contest(val id: Int = 0,
              val name: String = "",
              val type: String = "",
              val phase: PHASE? = null,
              val frozen: Boolean = false,
              val durationSeconds: Int = 0,
              val startTimeSeconds: Long = 0,
              val relativeTimeSeconds: Long = 0) {
    constructor() : this(0)

    companion object {
        fun fromJson(src: String): Contest {
            return Gson().fromJson(src, Contest::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }

    enum class PHASE {
        BEFORE,
        FINISHED
    }
}