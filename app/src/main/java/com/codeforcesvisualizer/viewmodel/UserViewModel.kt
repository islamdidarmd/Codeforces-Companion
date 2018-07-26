package com.codeforcesvisualizer.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.codeforcesvisualizer.api.ApiClient
import com.codeforcesvisualizer.api.ApiService
import com.codeforcesvisualizer.model.*
import com.codeforcesvisualizer.util.CONTEST_LIST_URL
import com.codeforcesvisualizer.util.USER_EXTRA_URL
import com.codeforcesvisualizer.util.USER_STATUS_URL
import com.codeforcesvisualizer.util.getProfileUrl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val TAG = "UserViewModel"

    private var userData: MutableLiveData<UserResponse> = MutableLiveData()
    private var userStatus: MutableLiveData<UserStatusResponse> = MutableLiveData()
    private var userExtra: MutableLiveData<UserExtraResponse> = MutableLiveData()

    //creating api client for network call
    private val apiClient = ApiClient.getClient()
            .create(ApiService::class.java)

    fun getData(): LiveData<UserResponse> {
        return userData
    }

    fun getStatus(): LiveData<UserStatusResponse> {
        return userStatus
    }

    fun getExtra(): LiveData<UserExtraResponse> {
        return userExtra
    }

    fun loadStatus(handle: String) {
        val call = apiClient.getUserStatus("$USER_STATUS_URL$handle")

        call.enqueue(object : Callback<UserStatusResponse> {
            override fun onResponse(call: Call<UserStatusResponse>?, response: Response<UserStatusResponse>?) {
                if (response?.body()?.status == STATUS.OK
                        && response.body()?.result != null) {

                    userStatus.value = response.body()
                } else {
                    userStatus.value = null
                }
            }

            override fun onFailure(call: Call<UserStatusResponse>?, t: Throwable?) {
                userStatus.value = null

                Log.d(TAG, t?.message.toString())
            }
        })
    }

    fun loadData(handle: String, handle2: String) {

        val call = apiClient.getUSers(getProfileUrl(handle, handle2))

        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>?, response: Response<UserResponse>?) {
                if (response?.body()?.status == STATUS.OK
                        && response.body()?.result != null) {

                    userData.value = response.body()
                } else {
                    userData.value = null
                }
            }

            override fun onFailure(call: Call<UserResponse>?, t: Throwable?) {
                userData.value = null

                Log.d(TAG, t?.message.toString())
            }
        })
    }

    fun loadExtra(handle: String) {

        val call = apiClient.getUserExtra("$USER_EXTRA_URL$handle")

        call.enqueue(object : Callback<UserExtraResponse> {
            override fun onResponse(call: Call<UserExtraResponse>?, response: Response<UserExtraResponse>?) {
                if (response?.body()?.status == STATUS.OK
                        && response.body()?.result != null) {

                    userExtra.value = response.body()
                } else {
                    userExtra.value = null
                }
            }

            override fun onFailure(call: Call<UserExtraResponse>?, t: Throwable?) {
                userExtra.value = null

                Log.d(TAG, t?.message.toString())
            }
        })
    }

}