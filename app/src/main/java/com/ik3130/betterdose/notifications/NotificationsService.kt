package com.ik3130.betterdose.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.ik3130.betterdose.MainActivity
import com.ik3130.betterdose.R


class NotificationsService(
    private val context: Context
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(title: String, content: String) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context, 1, activityIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon).setContentTitle(title)
            .setContentText(content).setContentIntent(activityPendingIntent).build()

        notificationManager.notify(kotlin.math.abs((1..9999999).random()), notification)
    }

    companion object {
        const val CHANNEL_ID: String = "3f78f667-99a0-4386-8307-029c536af890"
    }
}