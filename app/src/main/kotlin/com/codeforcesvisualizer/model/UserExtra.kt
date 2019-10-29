package com.codeforcesvisualizer.model

import com.google.gson.Gson

data class UserExtra(val contestId: Int,
                     val rank: Int,
                     val oldRating: Int,
                     val newRating: Int) {
}