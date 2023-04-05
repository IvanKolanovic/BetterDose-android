package com.ik3130.betterdose.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable


class Utils {
    companion object {
        @Composable
        fun ChangeVisibility(
            passwordVisible: Boolean, setPasswordVisible: (Boolean) -> Unit
        ) {
            val image = if (passwordVisible) Icons.Filled.Visibility
            else Icons.Filled.VisibilityOff

            // Please provide localized description for accessibility services
            val description = if (passwordVisible) "Hide password" else "Show password"

            IconButton(onClick = { setPasswordVisible(!passwordVisible) }) {
                Icon(imageVector = image, description)
            }
        }
    }
}