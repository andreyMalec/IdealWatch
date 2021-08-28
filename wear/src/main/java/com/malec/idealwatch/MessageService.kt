package com.malec.idealwatch

import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

class MessageService : WearableListenerService() {
    override fun onCreate() {
        super.onCreate()
        Log.e("wear", "MessageService: onCreate")
    }

    init {
        Log.e("wear", "MessageService: init")
    }

    override fun onMessageReceived(p0: MessageEvent) {
        Log.e("wear", "testMessage: $p0")
        super.onMessageReceived(p0)
    }
}