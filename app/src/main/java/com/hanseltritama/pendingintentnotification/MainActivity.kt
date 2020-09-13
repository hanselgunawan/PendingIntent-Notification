package com.hanseltritama.pendingintentnotification

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

        val progressMax = 100

        val notification: NotificationCompat.Builder = NotificationCompat.Builder(this,  "channel2ID")
            .setSmallIcon(R.drawable.ic_two)
            .setContentTitle("Download")
            .setContentText("Download in progress")
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setProgress(progressMax, 0, false)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(2, notification.build())

        Thread(Runnable {
            SystemClock.sleep(2000)
            for (progress in 0..progressMax step 10) {
                notification.setProgress(progressMax, progress, false)
                notificationManager.notify(2, notification.build())
                SystemClock.sleep(1000)
            }
            notification.setContentText("Download finished")
                .setProgress(0,0,false)
                .setOngoing(false)
            notificationManager.notify(2, notification.build())
        }).start()
    }
}
