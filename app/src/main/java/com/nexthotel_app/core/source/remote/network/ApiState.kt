package com.nexthotel_app.core.source.remote.network

sealed class ApiState<out R> {
    data class Success<out T>(val data: T) : ApiState<T>()
    data class Error(val message: String) : ApiState<Nothing>()
    object Loading : ApiState<Nothing>()
}