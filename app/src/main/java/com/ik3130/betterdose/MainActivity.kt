package com.ik3130.betterdose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ik3130.betterdose.firebase.AuthViewModel
import com.ik3130.betterdose.scheduler.AlarmSchedulerImpl
import com.ik3130.betterdose.ui.BetterDose
import com.ik3130.betterdose.ui.theme.BetterDoseTheme

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        val alarmScheduler = AlarmSchedulerImpl(this)
        authViewModel = AuthViewModel(auth = auth,alarmScheduler)

        setContent {
            //createNotificationChannel()
            val navController = rememberNavController()
            BetterDoseTheme(useDarkTheme = authViewModel.lightOrDarkMode()) {
                BetterDose(authViewModel, navController)
            }
        }
    }


}



