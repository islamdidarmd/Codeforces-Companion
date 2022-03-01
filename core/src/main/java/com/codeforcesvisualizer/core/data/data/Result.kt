package com.codeforcesvisualizer.core.data.data

sealed class Result<out T : Any> {
    object InProgress : Result<Nothing>()
    object Initial : Result<Nothing>()
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Failure(val error: AppError) : Result<Nothing>()
}