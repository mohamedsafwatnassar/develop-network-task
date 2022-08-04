package com.developnetwork.task.common.utils

sealed class DataState<out R> {

    data class Success<out T>(val data: T) : DataState<T>()
    data class SuccessMessage(val message: String) : DataState<Nothing>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    data class ErrorMessage(val error: String) : DataState<Nothing>()
    data class NormalError<out T>(val error: T) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
    object Finished : DataState<Nothing>()
}