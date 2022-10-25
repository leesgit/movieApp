package com.lbc.data.util

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel : ViewModel() {

    val handler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
}
