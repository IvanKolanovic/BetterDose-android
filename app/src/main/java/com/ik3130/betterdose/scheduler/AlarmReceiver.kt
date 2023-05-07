package com.ik3130.betterdose.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.ik3130.betterdose.notifications.NotificationsService

class AlarmReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
        println("ALARM TRIGGERED: $message")
        val notificationsService = NotificationsService(context)
        notificationsService.showNotification(message)
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}