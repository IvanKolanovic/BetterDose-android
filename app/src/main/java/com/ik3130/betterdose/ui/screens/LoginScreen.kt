package com.ik3130.betterdose.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ik3130.betterdose.R
import com.ik3130.betterdose.annotations.AuthNavGraph
import com.ik3130.betterdose.firebase.AuthViewModel
import com.ik3130.betterdose.firebase.UserLoginStatus
import com.ik3130.betterdose.ui.Utils.Companion.ChangeVisibility
import com.ik3130.betterdose.ui.components.CustomOutlinedTextField
import com.ik3130.betterdose.ui.screens.destinations.RegisterScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@AuthNavGraph(start = true)
@Destination
@Composable
fun LoginScreen(navigator: DestinationsNavigator, authViewModel: AuthViewModel) {
    val (email, setEmail) = remember { mutableStateOf("") }
    val (emailErrorTxt, setEmailErrorTxt) = remember { mutableStateOf("Email is not valid.") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (passwordErrorTxt, setPasswordErrorTxt) = remember { mutableStateOf("Password must not be empty or shorter than 4 characters.") }
    val (passwordVisible, setPasswordVisible) = remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    val loginStatus by authViewModel.userLoginStatus.collectAsState()

    LaunchedEffect(key1 = loginStatus) {
        when (loginStatus) {
            is UserLoginStatus.Failure -> {
                setPasswordErrorTxt("Login failed. Email or password is incorrect.")
                setEmailErrorTxt("Login failed. Email or password is incorrect.")
                emailError = true
                passwordError = true
            }
            UserLoginStatus.Successful -> {
                Log.i("Login SUCCESS", "Success")
                emailError = false
                passwordError = false
            }
            null -> {}
        }
    }

    fun validateEmail() {
        emailError = false

        if (email.trim().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        ) {
            emailError = true
            setEmailErrorTxt("Email is not valid.")
        }
    }

    fun validatePassword() {
        passwordError = false
        if (password.trim().isEmpty() || password.length < 5) {
            passwordError = true
            setPasswordErrorTxt("Password must not be empty or shorter than 4 characters.")
        }
    }

    fun validate() {
        validateEmail()
        validatePassword()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 35.dp),
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
        CustomOutlinedTextField(
            value = email,
            onValueChange = setEmail,
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            leadingIcon = {
                Icon(
                    Icons.Outlined.Email, contentDescription = "Email"
                )
            },
            isError = emailError,
            customErrorMessage = emailErrorTxt,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(16.dp))
        CustomOutlinedTextField(
            value = password,
            onValueChange = setPassword,
            label = { Text("Password") },
            trailingIcon = { ChangeVisibility(passwordVisible, setPasswordVisible) },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Info, contentDescription = "Info"
                )
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            customErrorMessage = passwordErrorTxt,
            isError = passwordError,
            modifier = Modifier.fillMaxWidth(),
            isPassword = true
        )
        Spacer(Modifier.size(16.dp))
        Button(onClick = {
            validate()
            if (!emailError && !passwordError) {
                authViewModel.executeLogin(email, password)
            }
        }) {
            Text("Sign In")
        }
        Spacer(Modifier.size(16.dp))
        Text(text = "Don't have an account?")
        TextButton(onClick = { navigator.navigate(RegisterScreenDestination) }) { Text("Sign up") }
    }
}
