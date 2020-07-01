package com.example.phonesmkkoding

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    val TAG = String::class.java.simpleName

    override fun onMessageReceived(p0: RemoteMessage) {
        Log.d(TAG, "From: " + p0.from)

        p0.data.isNotEmpty().let {
            Log.d(TAG, "Messaging data Payload : " + p0.data)

            if (!p0.data.isNullOrEmpty()) {
                val msg = p0.data["message"].toString()
                sendNotifikation(msg)
            }
        }

        p0.notification.let {
            if (it != null) {
                sendNotifikation(it.body!!)
            }
        }
    }

    private fun sendNotifikation(msg: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val chennelId = getString(R.string.default_notification_channel_id)
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val buildNotification =
            NotificationCompat.Builder(this, chennelId).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.fcm_message)).setContentText(msg)
                .setSound(soundUri).setContentIntent(pendingIntent)
        val notManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                chennelId,
                "Channel human readable title",
                NotificationManager.IMPORTANCE_HIGH
            )
            notManager.createNotificationChannel(channel)
        }
        notManager.notify(0, buildNotification.build())
    }

    override fun onNewToken(p0: String) {
        Log.d(TAG, "Refreshed Token: $p0")
    }
}