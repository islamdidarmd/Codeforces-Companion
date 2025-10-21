package com.codeforcesvisualizer.data.model

import com.codeforcesvisualizer.domain.entity.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoModel(
    @SerialName("handle")
    val handle: String,

    @SerialName("email")
    val email: String? = null,

    @SerialName("firstName")
    val firstName: String? = null,

    @SerialName("lastName")
    val lastName: String? = null,

    @SerialName("country")
    val country: String? = null,

    @SerialName("city")
    val city: String? = null,

    @SerialName("organization")
    val organization: String? = null,

    @SerialName("contribution")
    val contribution: Int,

    @SerialName("rank")
    val rank: String? = null,

    @SerialName("rating")
    val rating: Int? = null,

    @SerialName("maxRank")
    val maxRank: String? = null,

    @SerialName("maxRating")
    val maxRating: Int? = null,

    @SerialName("lastOnlineTimeSeconds")
    val lastOnlineTimeSeconds: Int,

    @SerialName("registrationTimeSeconds")
    val registrationTimeSeconds: Int,

    @SerialName("friendOfCount")
    val friendOfCount: Int,

    @SerialName("avatar")
    val avatar: String,

    @SerialName("titlePhoto")
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