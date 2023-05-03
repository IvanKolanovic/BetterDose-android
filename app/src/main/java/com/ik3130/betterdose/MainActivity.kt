package com.ik3130.betterdose

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ik3130.betterdose.firebase.AuthViewModel
import com.ik3130.betterdose.notifications.NotificationsService
import com.ik3130.betterdose.ui.BetterDose
import com.ik3130.betterdose.ui.theme.BetterDoseTheme

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        authViewModel = AuthViewModel(auth = auth)

        setContent {
            createNotificationChannel()
            val navController = rememberNavController()
            BetterDoseTheme(useDarkTheme = authViewModel.lightOrDarkMode()) {
                BetterDose(authViewModel, navController)
            }
        }
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Better Dose"
            val descriptionText = "Better Dose channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(NotificationsService.CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}



