package com.codeforcesvisualizer.model

import com.google.gson.Gson

data class ContestResponse(val status: STATUS = STATUS.FAILED, val result: List<Contest>? = null)