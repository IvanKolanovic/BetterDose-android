package com.ik3130.betterdose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.sp
import com.ik3130.betterdose.R
import com.ik3130.betterdose.annotations.AppNavGraph
import com.ik3130.betterdose.firebase.AuthViewModel
import com.ik3130.betterdose.ui.Utils
import com.ik3130.betterdose.ui.components.CustomOutlinedTextField
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@AppNavGraph
@Destination
@Composable
fun DeleteAccountScreen(navigator: DestinationsNavigator, authViewModel: AuthViewModel) {
    val (password, setPassword) = remember { mutableStateOf("") }
    val (repeatPassword, setRepeatPassword) = remember { mutableStateOf("") }
    val (passwordVisible, setPasswordVisible) = remember { mutableStateOf(false) }
    val (showDeleteAlertDialog, setShowDeleteAlertDialog) = remember { mutableStateOf(false) }
    val (showFailedAlertDialog, setShowFailedAlertDialog) = remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var repeatPasswordError by remember { mutableStateOf(false) }
    val (passwordErrorTxt, _) = remember { mutableStateOf("Password must not be empty or shorter than 4 characters.") }
    val (repeatPasswordErrorTxt, _) = remember { mutableStateOf("Passwords must match and not be empty.") }

    fun deleteAccount() {
        if (!passwordError && !repeatPasswordError) {
            val success = authViewModel.deleteAccount(password)
            if (!success)
                setShowFailedAlertDialog(true)
        }
    }

    @Composable
    fun ShowFailedAlertDialog() {
        AlertDialog(onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = {
                    setShowDeleteAlertDialog(false)
                    setShowFailedAlertDialog(false)
                }) { androidx.compose.material.Text(text = "OK") }
            },
            title = { androidx.compose.material.Text(text = "Failed to delete account.") },
            text = { androidx.compose.material.Text(text = "The account could not be deleted. Please re-enter your credentials.") })
    }

    @Composable
    fun ShowDeleteAlertDialog() {
        AlertDialog(onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = {
                    deleteAccount()
                }) { androidx.compose.material.Text(text = "OK") }
            },
            dismissButton = {
                TextButton(onClick = { setShowDeleteAlertDialog(false) }) {
                    androidx.compose.material.Text(
                        text = "Cancel"
                    )
                }
            },
            title = { androidx.compose.material.Text(text = "Please confirm") },
            text = { androidx.compose.material.Text(text = "Are you sure you want to delete your account? All data linked to the account will be deleted.") })
    }

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

    TopAppBar(backgroundColor = MaterialTheme.colorScheme.background,
        title = { androidx.compose.material.Text(text = "Delete Account") },
        navigationIcon = {
            IconButton(onClick = {
                navigator.popBackStack()
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back icon")
            }
        })
        if (showDeleteAlertDialog) ShowDeleteAlertDialog()
        if (showFailedAlertDialog) ShowFailedAlertDialog()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp),
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
            Spacer(Modifier.size(16.dp))
            CustomOutlinedTextField(
                value = password,
                onValueChange = setPassword,
                label = { Text("Password") },
                trailingIcon = { Utils.ChangeVisibility(passwordVisible, setPasswordVisible) },
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
                trailingIcon = { Utils.ChangeVisibility(passwordVisible, setPasswordVisible) },
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
            Button(
                onClick = {
                    validatePassword()
                    if (!passwordError && !repeatPasswordError) setShowDeleteAlertDialog(true)
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(horizontal = 20.dp)
            ) {
                androidx.compose.material.Text(
                    "Delete account", color = MaterialTheme.colorScheme.background, fontSize = 18.sp
                )
            }
        }

}