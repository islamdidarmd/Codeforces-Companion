package com.codeforcesvisualizer.core.data.data

open class AppError(val message: String)
open class ApiError(message: String) : AppError(message = message)
class InvalidApiResponseError : ApiError(message = "Invalid Response from Server")
class DataNotFoundError : ApiError(message = "No Data found")