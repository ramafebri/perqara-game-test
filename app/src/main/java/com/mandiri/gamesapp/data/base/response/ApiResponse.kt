package com.mandiri.gamesapp.data.base.response

//Use this class with coroutine flow
sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Error(val throwable: Throwable) : ApiResponse<Nothing>()
}