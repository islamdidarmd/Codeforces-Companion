package com.codeforcesvisualizer.model

import com.google.gson.Gson

data class Problem(val contestId: Int,
                   val index: String,
                   val tags: List<String>) {

    companion object {
        fun fromJson(src: String): Problem? {
            return Gson().fromJson(src, Problem::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }

}