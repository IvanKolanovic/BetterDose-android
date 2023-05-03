package com.ik3130.betterdose.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val service = NotificationsService(context)
        service.showNotification("", "")
    }
}