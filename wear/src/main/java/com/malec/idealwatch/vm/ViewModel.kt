package com.malec.idealwatch.vm

import androidx.annotation.MainThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class ViewModel {
    private val coroutine = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    val viewModelScope: CoroutineScope
        get() = coroutine

    open fun onCleared() {}

    @MainThread
    fun clear() {
        coroutine.cancel()
        onCleared()
    }
}