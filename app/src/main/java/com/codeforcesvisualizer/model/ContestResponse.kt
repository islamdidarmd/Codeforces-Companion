package com.codeforcesvisualizer.model

import com.google.gson.Gson

data class ContestResponse(val status: STATUS = STATUS.FAILED,
                           val result: List<Contest>? = null) {
    companion object {
        fun fromJson(src: String): ContestResponse? {
            return Gson().fromJson(src, ContestResponse::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }

    enum class STATUS {
        OK,
        FAILED
    }
}