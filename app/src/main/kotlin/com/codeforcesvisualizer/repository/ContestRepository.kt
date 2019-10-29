package com.codeforcesvisualizer.repository

import com.codeforcesvisualizer.Application
import com.codeforcesvisualizer.api.ApiClient
import com.codeforcesvisualizer.api.ApiService
import com.codeforcesvisualizer.model.ContestResponse
import com.codeforcesvisualizer.model.STATUS
import com.codeforcesvisualizer.util.SharedPrefsUtils.getData
import com.codeforcesvisualizer.util.SharedPrefsUtils.saveData
import com.codeforcesvisualizer.util.fromJson
import com.codeforcesvisualizer.util.toJson

class ContestRepository : BaseRepository() {
    suspend fun getAllContests(): ContestResponse? {

        try {
            val response = apiService.getContestsAsync()

            if (response.isSuccessful && response.body()?.status == STATUS.OK && response.body()?.result != null) {
                saveData(response.body()!!.toJson(), "contests")
                Application.contestResponse = response.body()!!
                return response.body()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //returning cached response when API call fails
        val cachedData: ContestResponse? = fromJson(getData("contests", ""))
        if (cachedData != null) {
            Application.contestResponse = cachedData
        }
        return cachedData
    }
}