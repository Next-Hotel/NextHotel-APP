package com.nexthotel_app.data

sealed class ResultResponse<out R> private constructor() {
    data class Success<out T>(val data: T) : ResultResponse<T>()
    data class Error(val error: String) : ResultResponse<Nothing>()
    object Loading : ResultResponse<Nothing>()
}