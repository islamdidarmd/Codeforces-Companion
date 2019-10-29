package com.codeforcesvisualizer.model

import com.google.gson.Gson

data class UserResponse(val status: STATUS = STATUS.FAILED, val result: List<User>? = null)