package com.ik3130.betterdose.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.ik3130.betterdose.MainActivity
import com.ik3130.betterdose.R
import kotlin.random.Random


class NotificationsService(
    private val context: Context
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(content: String) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context, 1, activityIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Time for medicine")
            .setAutoCancel(true).setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentText(content)
            .setContentIntent(activityPendingIntent).build()

        notificationManager.notify(Random.nextInt(), notification)
    }

    companion object {
        const val CHANNEL_ID: String = "3f78f667-99a0-4386-8307-029c536af890"
    }
}