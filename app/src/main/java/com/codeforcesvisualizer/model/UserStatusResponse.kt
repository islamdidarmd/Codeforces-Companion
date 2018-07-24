package com.codeforcesvisualizer.model

import com.google.gson.Gson

data class UserStatusResponse(val status: STATUS = STATUS.FAILED,
                              val result: List<UserStatus>? = null) {

    companion object {
        fun fromJson(src: String): UserStatusResponse? {
            return Gson().fromJson(src, UserStatusResponse::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }

}