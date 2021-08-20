package com.malec.idealwatch.update

class EngineLooper {
    private var isVisible = false
    private var isAmbientMode = false

    private var handler: EngineHandler? = null
    private var updatable: Updatable? = null

    fun setHandler(handler: EngineHandler) {
        this.handler = handler
    }

    fun setUpdatable(engine: Updatable) {
        this.updatable = engine
    }

    fun setVisibility(isVisible: Boolean) {
        this.isVisible = isVisible
    }

    fun setAmbientMode(isAmbientMode: Boolean) {
        this.isAmbientMode = isAmbientMode
    }

    fun update() {
        clear()
        if (shouldTimerBeRunning())
            handler?.sendEmptyMessage(EngineHandler.MSG_UPDATE_TIME)
    }

    fun clear() {
        handler?.removeMessages(EngineHandler.MSG_UPDATE_TIME)
    }

    fun delayedUpdate() {
        if (shouldTimerBeRunning()) {
            val timeMs = System.currentTimeMillis()
            val delayMs = INTERACTIVE_UPDATE_RATE_MS - timeMs % INTERACTIVE_UPDATE_RATE_MS
            handler?.sendEmptyMessageDelayed(EngineHandler.MSG_UPDATE_TIME, delayMs)
            updatable?.update()
        }
    }

    private fun shouldTimerBeRunning(): Boolean {
        return isVisible && !isAmbientMode
    }

    companion object {
        private const val INTERACTIVE_UPDATE_RATE_MS = 1000
    }
}