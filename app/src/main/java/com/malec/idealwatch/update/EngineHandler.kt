package com.malec.idealwatch.update

import android.os.Handler
import android.os.Message
import java.lang.ref.WeakReference

class EngineHandler(reference: EngineLooper) : Handler() {
    companion object {
        const val MSG_UPDATE_TIME = 0
    }

    private val reference: WeakReference<EngineLooper> = WeakReference(reference)

    override fun handleMessage(msg: Message) {
        val looper = reference.get()
        if (looper != null) {
            when (msg.what) {
                MSG_UPDATE_TIME -> looper.delayedUpdate()
            }
        }
    }
}