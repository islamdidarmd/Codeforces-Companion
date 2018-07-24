package com.codeforcesvisualizer.model

import com.google.gson.Gson

data class UserResponse(val status: STATUS = STATUS.FAILED,
                        val result: List<User>? = null) {

    companion object {
        fun fromJson(src: String): User? {
            return Gson().fromJson(src, User::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }

}