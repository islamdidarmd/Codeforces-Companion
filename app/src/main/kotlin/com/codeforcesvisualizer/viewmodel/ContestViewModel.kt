package com.codeforcesvisualizer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.content.Context
import android.util.Log
import com.codeforcesvisualizer.Application
import com.codeforcesvisualizer.api.ApiClient
import com.codeforcesvisualizer.api.ApiService
import com.codeforcesvisualizer.model.Contest
import com.codeforcesvisualizer.model.ContestResponse
import com.codeforcesvisualizer.model.STATUS
import com.codeforcesvisualizer.repository.ContestRepository
import com.codeforcesvisualizer.util.CONTEST_LIST_URL
import com.codeforcesvisualizer.util.fromJson
import com.codeforcesvisualizer.util.toJson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContestViewModel : ViewModel() {
    val TAG = "ContestViewModel"

    private val repository by lazy {
        ContestRepository()
    }

    private val contestData: MutableLiveData<ContestResponse> = MutableLiveData()

    fun getContests(): LiveData<ContestResponse> {
        reload()
        return contestData
    }

    fun reload() {
        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAllContests()
            contestData.postValue(data)
        }
    }
}