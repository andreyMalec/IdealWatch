package com.malec.idealwatch.update

import com.malec.idealwatch.storage.StateStorage
import kotlinx.coroutines.*

class EngineLooper(private val storage: StateStorage, private val scope: CoroutineScope) {
    private var updatable = mutableListOf<Updatable>()

    @Volatile
    private var updating = false

    fun addUpdatable(updatable: Updatable) {
        this.updatable.add(updatable)
    }

    fun removeUpdatable(updatable: Updatable) {
        this.updatable.remove(updatable)
    }

    fun start() {
        if (!updating) {
            updating = true

            scope.launch(Dispatchers.IO) {
                do {
                    if (shouldTimerBeRunning())
                        updatable.forEach {
                            it.update()
                        }
                    else
                        updating = false

                    delay(UPDATE_RATE)
                } while (updating)
            }
        }
    }

    fun stop() {
        updating = false
        scope.cancel()
    }

    private fun shouldTimerBeRunning(): Boolean {
        return storage.isVisible && !storage.isAmbientMode
    }

    companion object {
        private const val UPDATE_RATE = 1000L
    }
}