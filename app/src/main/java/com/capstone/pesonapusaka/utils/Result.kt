package com.capstone.pesonapusaka.utils

sealed class Result<T>(
    val data: T? = null,
    val error: String? = null
){
    class Success<T>(data: T): Result<T>(data)
    class Error<T>(error: String): Result<T>(error = error)
    class Loading<T>: Result<T>()
    class Void<T>: Result<T>()

}