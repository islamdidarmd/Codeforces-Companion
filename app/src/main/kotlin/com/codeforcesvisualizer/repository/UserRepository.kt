package com.codeforcesvisualizer.repository

import com.codeforcesvisualizer.model.STATUS
import com.codeforcesvisualizer.model.UserExtraResponse
import com.codeforcesvisualizer.model.UserResponse
import com.codeforcesvisualizer.model.UserStatusResponse
import com.codeforcesvisualizer.util.getProfileUrl

class UserRepository : BaseRepository() {

    suspend fun loadStatus(handle: String): UserStatusResponse? {

        return try {
            val response = apiService.getUserStatusAsync(handle)
            if (response.body()?.status == STATUS.OK && response.body()?.result != null) {
                response.body()?.handle = handle
                response.body()
            } else {
                null
            }

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun loadUserData(handle: String): UserResponse? {
        return try {
            val response = apiService.getUsersAsync(getProfileUrl(handle))
            if (response.body()?.status == STATUS.OK && response.body()?.result != null) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun loadExtra(handle: String): UserExtraResponse? {
        return try {
            val response = apiService.getUserExtraAsync(handle)
            if (response.body()?.status == STATUS.OK && response.body()?.result != null) {
                response.body()?.handle = handle
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}