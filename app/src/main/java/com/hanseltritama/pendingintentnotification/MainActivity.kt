package com.hanseltritama.pendingintentnotification

import android.app.Notification
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
            sendOnChannel2()
        }
    }

    private fun sendOnChannel1(title: String, message: String) {
        notificationHelper.sendOnChannel1()
    }

    private fun sendOnChannel2() {

        val title1 = "Title 1"
        val message1 = "Message 1"
        val title2 = "Title 2"
        val message2 = "Message 2 "

        val notification1: Notification = NotificationCompat.Builder(this,  "channel2ID")
            .setSmallIcon(R.drawable.ic_two)
            .setContentTitle(title1)
            .setContentText(message1)
            .setGroup("example_group")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        val notification2: Notification = NotificationCompat.Builder(this,  "channel2ID")
            .setSmallIcon(R.drawable.ic_two)
            .setContentTitle(title2)
            .setContentText(message2)
            .setGroup("example_group")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        val summaryNotification: Notification = NotificationCompat.Builder(this,  "channel2ID")
            .setSmallIcon(R.drawable.ic_reply)
            .setStyle(NotificationCompat.InboxStyle()
                .addLine("$title2 $message2")
                .addLine("$title1 $message1")
                .setBigContentTitle("2 New Messages")
                .setSummaryText("user@example.com"))
            .setGroup("example_group")
            .setGroupSummary(true)
            .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_CHILDREN)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)

        SystemClock.sleep(2000)
        notificationManager.notify(2, notification1)
        SystemClock.sleep(2000)
        notificationManager.notify(3, notification2)
        SystemClock.sleep(2000)
        notificationManager.notify(4, summaryNotification)
    }
}
