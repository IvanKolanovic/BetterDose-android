package com.ik3130.betterdose.utils.permissions

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat.startActivity

@Composable
fun PermissionDialog(context: Context) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = true
            ),
            title = { Text("Permission Needed") },
            text = {
                Text("Allow BetterDose to access Do Not Disturb Settings? By pressing 'No' the app won't send any notifications.")
            },
            onDismissRequest = { openDialog.value = true },

            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xffFF9800))
                ) {
                    Text(text = "No")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                        startActivity(context, intent, null)
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xffFF9800))
                ) {
                    Text(text = "Yes")
                }
            }
        )
    }
}