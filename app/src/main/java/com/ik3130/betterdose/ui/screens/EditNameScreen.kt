package com.ik3130.betterdose.ui.screens

import android.text.TextUtils
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ik3130.betterdose.R
import com.ik3130.betterdose.annotations.AppNavGraph
import com.ik3130.betterdose.firebase.AuthViewModel
import com.ik3130.betterdose.ui.components.CustomOutlinedTextField
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@AppNavGraph
@Destination
@Composable
fun EditNameScreen(navigator: DestinationsNavigator, authViewModel: AuthViewModel) {
    val (newName, setNewName) = remember { mutableStateOf("") }
    val (nameError, setNameError) = remember { mutableStateOf(false) }

    fun validateName() {
        if (TextUtils.isEmpty(newName.trim())) setNameError(true)
        else {
            authViewModel.updateName(newName.trim())
            navigator.popBackStack()
        }
    }

    TopAppBar(backgroundColor = MaterialTheme.colorScheme.surface,
        title = {
            androidx.compose.material.Text(
                text = "Change full name",
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                navigator.popBackStack()
            }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back icon")
            }
        })
    Box {
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
            CustomOutlinedTextField(
                value = newName,
                onValueChange = setNewName,
                label = { Text("Full name") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = nameError,
                customErrorMessage = "Full name cannot be empty."
            )
            Spacer(Modifier.size(16.dp))
            Button(
                onClick = { validateName() }, modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .padding(horizontal = 20.dp)
            ) {
                androidx.compose.material.Text(
                    "Update full name",
                    fontSize = 18.sp, color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}