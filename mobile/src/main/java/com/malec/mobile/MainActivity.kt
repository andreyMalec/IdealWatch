package com.malec.mobile

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : Activity(), MessageClient.OnMessageReceivedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.text).setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                sendMessage("test", nodes().first())
            }
        }
    }

    override fun onResume() {
        super.onResume()

        Wearable.getMessageClient(this).addListener(this)
    }

    override fun onPause() {
        super.onPause()

        Wearable.getMessageClient(this).removeListener(this)
    }

    private suspend fun nodes(): List<Node> =
        Wearable.getNodeClient(this)
            .connectedNodes.await()

    private suspend fun sendMessage(message: String, node: Node) {
        Wearable.getMessageClient(this)
            .sendMessage(nodes().first().id, "/path", message.toByteArray())
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        Log.e("TAG", "messageEvent: $messageEvent")
    }
}