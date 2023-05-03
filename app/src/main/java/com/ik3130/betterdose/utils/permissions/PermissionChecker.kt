package com.ik3130.betterdose.utils.permissions

import android.app.NotificationManager
import android.content.Context
import androidx.compose.runtime.Composable

@Composable
fun checkNotificationPolicyAccess(
    notificationManager: NotificationManager,
    context: Context
): Boolean {
    if (notificationManager.isNotificationPolicyAccessGranted) {
        return true
    } else {
        PermissionDialog(context)
    }
    return false
}