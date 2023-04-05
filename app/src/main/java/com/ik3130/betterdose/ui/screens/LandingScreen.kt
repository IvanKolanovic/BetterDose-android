package com.ik3130.betterdose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ik3130.betterdose.R
import com.ik3130.betterdose.ui.Utils.Companion.ChangeVisibility

@Composable
fun LandingScreen() {

    val (email, setEmail) = remember { mutableStateOf("") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (passwordVisible, setPasswordVisible) = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.betterdose_logo),
            contentDescription = "BetterDose Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(200.dp)
                .height(100.dp)
        )
        Text("SIGN IN")
        OutlinedTextField(value = email,
            onValueChange = setEmail,
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = {
                Icon(
                    Icons.Outlined.Email, contentDescription = "Email"
                )
            })
        Spacer(Modifier.size(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = setPassword,
            label = { Text("Password") },
            trailingIcon = { ChangeVisibility(passwordVisible,setPasswordVisible) },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Info, contentDescription = "Info"
                )
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )
        Spacer(Modifier.size(16.dp))
        Button(onClick = { }) {
            Text("Sign In")
        }
        Spacer(Modifier.size(16.dp))
        Text(text = "Don't have an account?")
        TextButton(onClick = { /* Do something! */ }) { Text("Sign up") }
    }
}
