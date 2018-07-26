package com.codeforcesvisualizer.model

import com.google.gson.Gson

data class UserExtraResponse(val status: STATUS = STATUS.FAILED,
                             val result: List<UserExtra>? = null) {

    companion object {
        fun fromJson(src: String): UserExtraResponse? {
            return Gson().fromJson(src, UserExtraResponse::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }

}