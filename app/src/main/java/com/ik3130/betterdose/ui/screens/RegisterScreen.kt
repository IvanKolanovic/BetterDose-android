package com.ik3130.betterdose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.ik3130.betterdose.ui.Utils.Companion.ChangeVisibility
import com.ik3130.betterdose.ui.components.CustomOutlinedTextField
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@AuthNavGraph
@Destination
@Composable
fun RegisterScreen(navigator: DestinationsNavigator, authViewModel: AuthViewModel) {
    val (fullName, setFullName) = remember { mutableStateOf("") }
    val (email, setEmail) = remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var nameError by remember { mutableStateOf(false) }
    val (emailErrorTxt, _) = remember { mutableStateOf("Email is not valid.") }
    val (password, setPassword) = remember { mutableStateOf("") }
    val (repeatPassword, setRepeatPassword) = remember { mutableStateOf("") }
    val (passwordVisible, setPasswordVisible) = remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var repeatPasswordError by remember { mutableStateOf(false) }
    val (passwordErrorTxt, _) = remember { mutableStateOf("Password must not be empty or shorter than 4 characters.") }
    val (repeatPasswordErrorTxt, _) = remember { mutableStateOf("Passwords must match and not be empty.") }

    fun validatePassword() {
        repeatPasswordError = false
        passwordError = false
        if (password.trim().isEmpty() || password.length < 5) {
            passwordError = true
        }

        if (password.trim().isEmpty() || password.trim() != repeatPassword.trim()) {
            repeatPasswordError = true
        }
    }

    fun validateEmail() {
        emailError = false
        if (email.trim().isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches()
        ) {
            emailError = true
        }
    }

    fun validateName() {
        nameError = false
        if (fullName.trim().isEmpty()) {
            nameError = true
        }
    }


    fun validate() {
        validateEmail()
        validateName()
        validatePassword()
    }

    TopAppBar(backgroundColor = MaterialTheme.colorScheme.background,
        title = { androidx.compose.material.Text(text = "Register") },
        navigationIcon = {
            IconButton(onClick = {
                navigator.popBackStack()
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back icon")
            }
        })

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
        Spacer(Modifier.size(8.dp))
        CustomOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = fullName,
            onValueChange = setFullName,
            label = { Text("Full Name") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            isError = nameError,
            customErrorMessage = "Name must not be empty."
        )
        Spacer(Modifier.size(16.dp))
        CustomOutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
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
            customErrorMessage = emailErrorTxt
        )
        Spacer(Modifier.size(16.dp))
        CustomOutlinedTextField(value = password,
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
        CustomOutlinedTextField(
            value = repeatPassword,
            onValueChange = setRepeatPassword,
            label = { Text("Repeat Password") },
            trailingIcon = { ChangeVisibility(passwordVisible, setPasswordVisible) },
            leadingIcon = {
                Icon(
                    Icons.Outlined.Info, contentDescription = "Info"
                )
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            customErrorMessage = repeatPasswordErrorTxt,
            isError = repeatPasswordError,
            modifier = Modifier.fillMaxWidth(),
            isPassword = true
        )
        Spacer(Modifier.size(16.dp))
        Button(onClick = {
            validate()
            if (!emailError && !nameError && !passwordError && !repeatPasswordError) authViewModel.executeRegister(
                email, password, fullName
            )
        }) {
            Text("Register")
        }
    }
}

