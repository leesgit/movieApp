 
package com.lbc.data.util

sealed class LocalResult<out T> {

    object Success : LocalResult<Nothing>()
    data class Error(val message: String) : LocalResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success"
            is Error -> "Error[exception=$message]"
        }
    }
}
