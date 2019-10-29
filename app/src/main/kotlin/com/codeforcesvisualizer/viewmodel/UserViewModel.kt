package com.codeforcesvisualizer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import com.codeforcesvisualizer.api.ApiClient
import com.codeforcesvisualizer.api.ApiService
import com.codeforcesvisualizer.model.*
import com.codeforcesvisualizer.repository.UserRepository
import com.codeforcesvisualizer.util.CONTEST_LIST_URL
import com.codeforcesvisualizer.util.USER_EXTRA_URL
import com.codeforcesvisualizer.util.USER_STATUS_URL
import com.codeforcesvisualizer.util.getProfileUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val repository by lazy {
        UserRepository()
    }

    private var userData: MutableLiveData<UserResponse> = MutableLiveData()
    private var userStatus: MutableLiveData<UserStatusResponse> = MutableLiveData()
    private var userExtra: MutableLiveData<UserExtraResponse> = MutableLiveData()

    //These are using for comparing multiple user
    private var userExtras: MutableLiveData<List<UserExtraResponse>> = MutableLiveData()
    private var userStatuses: MutableLiveData<List<UserStatusResponse>> = MutableLiveData()


    fun getData(): LiveData<UserResponse> {
        return userData
    }

    fun getStatus(): LiveData<UserStatusResponse> {
        return userStatus
    }

    fun getExtra(): LiveData<UserExtraResponse> {
        return userExtra
    }

    fun getExtras(): LiveData<List<UserExtraResponse>> {
        return userExtras
    }

    fun getStatuses(): LiveData<List<UserStatusResponse>> {
        return userStatuses
    }

    fun loadStatus(handle: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.loadStatus(handle)
            userStatus.postValue(data)
        }
    }

    fun loadData(handle: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.loadUserData(handle)
            userData.postValue(data)
        }
    }

    fun loadExtra(handle: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.loadExtra(handle)
            userExtra.postValue(data)
        }
    }

    fun loadExtra(handle1: String, handle2: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val data1 = repository.loadExtra(handle1)

            if (data1 == null) {
                //api call failed for first user. Let's exit from here
                userExtras.value = null
                return@launch
            }

            val data2 = repository.loadExtra(handle2)

            if (data2 == null) {
                //api call failed for second user. Let's exit from here
                userExtras.value = null
                return@launch
            }

            userExtras.postValue(arrayListOf(data1, data2))
        }
    }

    fun loadStatus(handle1: String, handle2: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val data1 = repository.loadStatus(handle1)

            if (data1 == null) {
                //api call failed for first user. Let's exit from here
                userStatuses.value = null
                return@launch
            }

            val data2 = repository.loadStatus(handle2)

            if (data2 == null) {
                //api call failed for second user. Let's exit from here
                userStatuses.value = null
                return@launch
            }

            userStatuses.postValue(arrayListOf(data1, data2))
        }
    }

}