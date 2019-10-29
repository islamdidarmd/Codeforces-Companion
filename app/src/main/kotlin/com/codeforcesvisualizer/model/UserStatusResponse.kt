package com.codeforcesvisualizer.model

import com.google.gson.Gson

data class UserStatusResponse(val status: STATUS = STATUS.FAILED,
                              val result: List<UserStatus>? = null,
                              var handle: String = "")