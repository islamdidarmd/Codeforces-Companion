package com.codeforcesvisualizer.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import com.codeforcesvisualizer.Application
import com.codeforcesvisualizer.api.ApiClient
import com.codeforcesvisualizer.api.ApiService
import com.codeforcesvisualizer.model.Contest
import com.codeforcesvisualizer.model.ContestResponse
import com.codeforcesvisualizer.model.STATUS
import com.codeforcesvisualizer.util.CONTEST_LIST_URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContestList : ViewModel() {
    val TAG = "ContestList"

    private var contestData: MutableLiveData<ContestResponse> = MutableLiveData()

    fun getContests(): LiveData<ContestResponse> {
        if (contestData.value == null) {
            contestData.value = Application.contestResponse
        }

        //if contest data is still null try retrieving from shared prefs
        if (contestData.value == null) {
            Application.contestResponse = ContestResponse.fromJson(Application.getData("contests", ""))
            contestData.value = Application.contestResponse
        }

        return contestData
    }

    fun loadData() {
        val apiClient = ApiClient.getClient()
                .create(ApiService::class.java)

        val call = apiClient.getContests(CONTEST_LIST_URL)
        call.enqueue(object : Callback<ContestResponse> {
            override fun onResponse(call: Call<ContestResponse>?, response: Response<ContestResponse>?) {
                if (response?.body()?.status == STATUS.OK
                        && response.body()?.result != null) {

                    // New contest list available. Save it to Application for later use and update the value to notify observers
                    Application.contestResponse = response.body()!!
                    contestData.value = response.body()

                    Application.saveData(response.body()!!.toJson(), "contests")

                    Log.d(TAG, "On response value")
                }else{
                    contestData.value = null
                }
            }

            override fun onFailure(call: Call<ContestResponse>?, t: Throwable?) {
                if (Application.contestResponse == null) {
                    contestData.value = null

                    Log.d(TAG, "On onFailure value")
                }

                Log.d(TAG, t?.message.toString())
            }
        })
    }
}