 
package com.lbc.data.util

sealed class RemoteResult<out T> {

    data class Success<out T>(val data: T) : RemoteResult<T>()
    data class Error(val message: String) : RemoteResult<Nothing>()
    object NotFound : RemoteResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$message]"
            NotFound -> "NotFound"
        }
    }
}
val <T> RemoteResult<T>.data: T?
    get() = (this as? RemoteResult.Success)?.data
