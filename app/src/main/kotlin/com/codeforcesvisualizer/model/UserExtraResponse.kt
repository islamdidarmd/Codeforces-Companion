package com.codeforcesvisualizer.model

import com.google.gson.Gson

data class UserExtraResponse(val status: STATUS = STATUS.FAILED,
                             val result: List<UserExtra>? = null,
                             var handle: String) {

}