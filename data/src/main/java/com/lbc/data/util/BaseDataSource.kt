package com.lbc.data.util

import android.util.Log
import retrofit2.Response

abstract class BaseDataSource {
    suspend fun <T> getRemoteResult(call: suspend () -> Response<T>): RemoteResult<T> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                RemoteResult.Success(response.body()!!)
            } else if (response.code() == NOT_FOUND) {
                RemoteResult.Error(response.message() ?: "NotFound")
            } else {
                RemoteResult.Error(response.message() ?: "Error")
            }
        } catch (e: Exception) {
            Log.e("exception", e.toString())
            RemoteResult.Error(e.toString())
        }
    }

    suspend fun <T> getLocalResult(
        call: suspend () -> T
    ): LocalResult<T> {
        return try {
            call.invoke()
            LocalResult.Success
        } catch (e: Exception) {
            Log.e("exception", e.toString())
            LocalResult.Error(e.toString())
        }
    }
}
