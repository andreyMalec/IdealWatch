package com.malec.idealwatch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.tasks.await

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val messageClient = Wearable.getMessageClient(this)
        messageClient.addListener { messageEvent ->
            Log.i("TAG", "messageEvent: $messageEvent")
        }
    }

    private suspend fun nodes(): List<Node> =
        Wearable.getNodeClient(this)
            .connectedNodes.await()

    private suspend fun sendMessage(message: String, node: Node) {
        Wearable.getMessageClient(this)
            .sendMessage(nodes().first().id, "/path", message.toByteArray())
    }
}