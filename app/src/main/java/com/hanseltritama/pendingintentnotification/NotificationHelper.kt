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
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.Person
import androidx.core.app.RemoteInput

class NotificationHelper(base: Context) : ContextWrapper(base) {
    private val channel1ID: String = "channel1ID"
    private val channel1Name: String = "channel1Name"
    private val channel2ID: String = "channel2ID"
    private val channel2Name: String = "channel2Name"
    private var notificationManager: NotificationManager? = null
    private val mediaSession: MediaSessionCompat = MediaSessionCompat(this, "tag")

    init {
        createChannels()
        messages.add(Message("Good Morning!", "Jim"))
        messages.add(Message("Hello", null))
        messages.add(Message("Hi!", "Jenny"))
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

    fun sendOnChannel1() {
        getChannel1Notification(this)
    }

    companion object {
        var messages: ArrayList<Message> = ArrayList()

        fun getChannel1Notification(context: Context) {

            val resultIntent = Intent(context, MainActivity::class.java)
            val resultPendingIntent = PendingIntent.getActivity(context,
                1,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)

            // add an input field inside of the notification
            val remoteInput: RemoteInput = RemoteInput.Builder("key_text_reply")
                .setLabel("Your answer...")
                .build()

            val replyIntent: Intent
            var replyPendingIntent: PendingIntent? = null

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                replyIntent = Intent(context, NotificationReceiver::class.java)
                replyPendingIntent = PendingIntent.getBroadcast(context,
                    0,
                    replyIntent,
                    0)
            }

            val replyAction: NotificationCompat.Action = NotificationCompat.Action.Builder(
                R.drawable.ic_reply,
                "Reply",
                replyPendingIntent
            ).addRemoteInput(remoteInput).build()


            val person: Person = Person.Builder().setName("Me").build()
            val messagingStyle: NotificationCompat.MessagingStyle = NotificationCompat.MessagingStyle(person)
            messagingStyle.conversationTitle = "Group Chat"

            for (chatMessage in messages) {
                val senderName: Person = Person.Builder().setName(chatMessage.getSender()).build()
                messagingStyle.addMessage(
                    NotificationCompat.MessagingStyle.Message(
                        chatMessage.getText(),
                        chatMessage.getTimestamp(),
                        senderName
                    )
                )
            }

            val notification = NotificationCompat.Builder(context, "channel1ID")
                .setSmallIcon(R.drawable.ic_one)
                .setStyle(messagingStyle)
                .addAction(replyAction)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setColor(Color.BLUE)
                .setContentIntent(resultPendingIntent)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true) // notification will disappear when tapped


            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(1, notification.build())
        }
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