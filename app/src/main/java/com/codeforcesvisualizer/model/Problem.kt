package com.codeforcesvisualizer.model

import com.google.gson.Gson

data class Problem(val contestId: Int,
                   val index: String,
                   val tags: List<String> = ArrayList()) {

    companion object {
        fun fromJson(src: String): Problem? {
            return Gson().fromJson(src, Problem::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }

    fun getProblemName(): String {
        return "$contestId-$index"
    }

}