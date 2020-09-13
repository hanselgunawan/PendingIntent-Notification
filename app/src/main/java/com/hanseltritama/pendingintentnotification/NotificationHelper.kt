package com.hanseltritama.pendingintentnotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.session.MediaSession
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat

class NotificationHelper(base: Context) : ContextWrapper(base) {
    private val channel1ID: String = "channel1ID"
    private val channel1Name: String = "channel1Name"
    private val channel2ID: String = "channel2ID"
    private val channel2Name: String = "channel2Name"
    private var notificationManager: NotificationManager? = null
    private val mediaSession: MediaSessionCompat = MediaSessionCompat(this, "tag")

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

        val picture = BitmapFactory.decodeResource(resources, R.drawable.puppy)

        return NotificationCompat.Builder(applicationContext,  channel1ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_one)
            .setLargeIcon(picture)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(picture)
                .bigLargeIcon(null)) // it will remove the thumbnail when expanded
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.BLUE)
            .setContentIntent(resultPendingIntent)
            .setOnlyAlertOnce(true)
            .setAutoCancel(true) // notification will disappear when tapped
    }

    fun getChannel2Notification(title: String, message: String) : NotificationCompat.Builder {
        val resultIntent = Intent(this, MainActivity::class.java)
        val resultPendingIntent = PendingIntent.getActivity(this, 2, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val artwork = BitmapFactory.decodeResource(resources, R.drawable.puppy)

        return NotificationCompat.Builder(applicationContext,  channel2ID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ic_two)
            .setLargeIcon(artwork)
            .addAction(R.drawable.ic_dislike,"Dislike", null)
            .addAction(R.drawable.ic_previous,"Previous", null)
            .addAction(R.drawable.ic_pause,"Pause", null)
            .addAction(R.drawable.ic_next,"Next", null)
            .addAction(R.drawable.ic_like,"Like", null)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(1,2,3)
                .setMediaSession(mediaSession.sessionToken))
            .setSubText("Sub Text")
            .setContentIntent(resultPendingIntent)
            .setAutoCancel(true)
    }

}