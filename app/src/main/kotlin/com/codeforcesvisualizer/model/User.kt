package com.codeforcesvisualizer.model

import com.google.gson.Gson

data class User(val firstName: String = "",
                val lastName: String = "",
                val country: String = "",
                val lastOnlineTimeSeconds: Long = 0L,
                val city: String = "",
                val rating: Int = 0,
                val friendOfCount: Int = 0,
                val titlePhoto: String = "",
                val handle: String = "",
                val avatar: String = "",
                val contribution: String = "",
                val organization: String = "",
                val rank: String = "",
                val maxRating: String = "",
                val registrationTimeSeconds: Long = 0L,
                val maxRank: String = "") {
}