package com.codeforcesvisualizer.model

import com.google.gson.Gson

data class Problem(val contestId: Int,
                   val index: String,
                   val tags: List<String> = ArrayList()) {

    fun getProblemName(): String {
        return "$contestId-$index"
    }

}