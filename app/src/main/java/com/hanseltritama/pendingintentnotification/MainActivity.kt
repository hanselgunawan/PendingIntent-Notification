package com.hanseltritama.pendingintentnotification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var notificationHelper: NotificationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationHelper = NotificationHelper(this)

        btnChannel1.setOnClickListener {
            sendOnChannel1(title_field.text.toString(), message_field.text.toString())
        }

        btnChannel2.setOnClickListener {
            sendOnChannel2(title_field.text.toString(), message_field.text.toString())
        }
    }

    private fun sendOnChannel1(title: String, message: String) {
        notificationHelper.sendOnChannel1()
    }

    private fun sendOnChannel2(title: String, message: String) {
        val notificationBuilder: NotificationCompat.Builder = notificationHelper.getChannel2Notification(title, message)
        notificationHelper.getManager().notify(2, notificationBuilder.build())
    }
}
