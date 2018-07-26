package com.codeforcesvisualizer.model

import com.google.gson.Gson

data class UserExtra(val contestId: Int,
                     val rank: Int,
                     val oldRating: Int,
                     val newRating: Int) {
    companion object {
        fun fromJson(src: String): UserExtra? {
            return Gson().fromJson(src, UserExtra::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}