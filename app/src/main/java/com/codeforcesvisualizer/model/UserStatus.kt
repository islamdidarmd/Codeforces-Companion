package com.codeforcesvisualizer.model

import com.google.gson.Gson

data class UserStatus(val programmingLanguage: String,
                      var verdict: String,
                      val problem: Problem) {

    companion object {
        fun fromJson(src: String): UserStatus? {
            return Gson().fromJson(src, UserStatus::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}