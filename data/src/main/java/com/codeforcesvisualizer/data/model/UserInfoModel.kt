package com.codeforcesvisualizer.data.model

import com.codeforcesvisualizer.domain.entity.User
import com.squareup.moshi.Json

data class UserInfoModel(
    @field:Json(name = "handle")
    val handle: String,

    @field:Json(name = "email")
    val email: String?,

    @field:Json(name = "firstName")
    val firstName: String?,

    @field:Json(name = "lastName")
    val lastName: String?,

    @field:Json(name = "country")
    val country: String?,

    @field:Json(name = "city")
    val city: String?,

    @field:Json(name = "organization")
    val organization: String?,

    @field:Json(name = "contribution")
    val contribution: Int,

    @field:Json(name = "rank")
    val rank: String?,

    @field:Json(name = "rating")
    val rating: Int?,

    @field:Json(name = "maxRank")
    val maxRank: String?,

    @field:Json(name = "maxRating")
    val maxRating: Int?,

    @field:Json(name = "lastOnlineTimeSeconds")
    val lastOnlineTimeSeconds: Int,

    @field:Json(name = "registrationTimeSeconds")
    val registrationTimeSeconds: Int,

    @field:Json(name = "friendOfCount")
    val friendOfCount: Int,

    @field:Json(name = "avatar")
    val avatar: String,

    @field:Json(name = "titlePhoto")
    val titlePhoto: String,
) {
    fun toEntity(): User {
        return User(
            handle = handle,
            email = email ?: "",
            firstName = firstName ?: "",
            lastName = lastName ?: "",
            country = country ?: "",
            city = city ?: "",
            organization = organization ?: "",
            contribution = contribution,
            rank = rank ?: "",
            rating = rating ?: 0,
            maxRank = maxRank ?: "",
            maxRating = maxRating ?: 0,
            lastOnlineTimeSeconds = lastOnlineTimeSeconds,
            registrationTimeSeconds = registrationTimeSeconds,
            friendOfCount = friendOfCount,
            avatar = avatar,
            titlePhoto = titlePhoto
        )
    }
}