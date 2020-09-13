package com.hanseltritama.pendingintentnotification

import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val remoteInput: Bundle = RemoteInput.getResultsFromIntent(intent)

        if (remoteInput != null) {
            val replyText: CharSequence? = remoteInput.getCharSequence("key_text_reply")
            val answer = Message(replyText ?: "", null)
            NotificationHelper.messages.add(answer)
            NotificationHelper.getChannel1Notification(context!!)
        }
    }

}