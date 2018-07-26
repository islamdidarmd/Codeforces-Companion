package com.codeforcesvisualizer.model

import com.google.gson.Gson

data class UserResponse(val status: STATUS = STATUS.FAILED,
                        val result: List<User>? = null) {

    companion object {
        fun fromJson(src: String): UserResponse? {
            return Gson().fromJson(src, UserResponse::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }

}