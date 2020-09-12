package com.hanseltritama.pendingintentnotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.core.app.NotificationCompat

class NotificationHelper(base: Context) : ContextWrapper(base) {
    private val channel1ID: String = "channel1ID"
    private val channel1Name: String = "channel1Name"
    private val channel2ID: String = "channel2ID"
    private val channel2Name: String = "channel2Name"
    private var notificationManager: NotificationManager? = null

    init {
        createChannels()
    }

    fun createChannels() {
        val channel1 = NotificationChannel(channel1ID, channel1Name, NotificationManager.IMPORTANCE_DEFAULT)
        channel1.enableLights(true)
        channel1.enableVibration(true)
        channel1.lightColor = R.color.colorPrimary

        // Show this notification on all lockscreens, but conceal sensitive or private information on secure lockscreens
        channel1.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        getManager().createNotificationChannel(channel1)

        val channel2 = NotificationChannel(channel2ID, channel2Name, NotificationManager.IMPORTANCE_DEFAULT)
        channel2.enableLights(true)
        channel2.enableVibration(true)
        channel2.lightColor = R.color.colorPrimary
        channel2.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        getManager().createNotificationChannel(channel2)
    }

    fun getManager() : NotificationManager {
        if (notificationManager == null) {
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }

        return notificationManager as NotificationManager
    }

    fun getChannel1Notification(title: String, message: String) : NotificationCompat.Builder {

        val resultIntent = Intent(this, MainActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(this,
            1,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        val broadcastIntent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage", message)

        val actionIntent = PendingIntent.getBroadcast(this,
            0,
            broadcastIntent,
            // to update toastMessage extra's value
            PendingIntent.FLAG_UPDATE_CURRENT)

        val largeIcon = BitmapFactory.decodeResource(resources, R.drawable.puppy)

        return NotificationCompat.Builder(applicationContext,  channel1ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_one)
            .setLargeIcon(largeIcon)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(getString(R.string.dummy_text))
                .setBigContentTitle("Big Content Title")
                .setSummaryText("Summary Text"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.BLUE)
            .setContentIntent(resultPendingIntent)
            .setOnlyAlertOnce(true)
            .setAutoCancel(true) // notification will disappear when tapped
            .addAction(R.mipmap.ic_launcher, "Toast", actionIntent) // can be added up to 3
    }

    fun getChannel2Notification(title: String, message: String) : NotificationCompat.Builder {
        val resultIntent = Intent(this, MainActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(this, 2, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Builder(applicationContext,  channel2ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_two)
            .setStyle(NotificationCompat.InboxStyle()
                .addLine("This is line 1")
                .addLine("This is line 2")
                .addLine("This is line 3")
                .addLine("This is line 4")
                .addLine("This is line 5")
                .addLine("This is line 6")
                .addLine("This is line 7")
                .addLine("This is line 8")
                .setBigContentTitle("Big Content Title")
                .setSummaryText("Summary Text"))
            .setContentIntent(resultPendingIntent)
            .setAutoCancel(true)
    }

}