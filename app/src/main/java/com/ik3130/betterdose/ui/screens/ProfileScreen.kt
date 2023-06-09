package com.ik3130.betterdose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ik3130.betterdose.R
import com.ik3130.betterdose.annotations.AppNavGraph
import com.ik3130.betterdose.firebase.AuthViewModel
import com.ik3130.betterdose.ui.screens.destinations.DeleteAccountScreenDestination
import com.ik3130.betterdose.ui.screens.destinations.EditNameScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@AppNavGraph
@Destination
@Composable
fun ProfileScreen(navigator: DestinationsNavigator, authViewModel: AuthViewModel) {

    TopAppBar(
        backgroundColor = MaterialTheme.colorScheme.surface,
        title = { Text(text = "Profile", color = MaterialTheme.colorScheme.onSurface) },
    )
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(30.dp), verticalArrangement = Arrangement.Center
        ) {
            Box {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.betterdose_logo),
                        contentDescription = "BetterDose Logo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )
                    Text(
                        text = authViewModel.firestoreUser?.fullName ?: "ne radi",
                        fontSize = 18.sp, color = MaterialTheme.colorScheme.scrim,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )
                    Text(
                        text = "Email: ${authViewModel.currentUser?.email.toString()}",
                        fontSize = 14.sp, color = MaterialTheme.colorScheme.scrim
                    )

                    Spacer(Modifier.size(14.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Switch(
                            checked = authViewModel.currentColorMode, onCheckedChange = {
                                authViewModel.setCurrentColorMode(it)
                            }, colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.primary,
                                checkedTrackColor = MaterialTheme.colorScheme.primary
                            )
                        )
                        Text(
                            text = "Dark mode", color = MaterialTheme.colorScheme.scrim
                        )
                    }
                    Button(
                        onClick = { navigator.navigate(EditNameScreenDestination) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    ) {
                        Text(
                            "Edit full name",
                            fontSize = 18.sp, color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(Modifier.size(16.dp))
                    Button(
                        onClick = { navigator.navigate(DeleteAccountScreenDestination) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    ) {
                        Text(
                            "Delete Account",
                            fontSize = 18.sp, color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(Modifier.size(16.dp))
                    Button(
                        onClick = { authViewModel.executeSignOut() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                    ) {
                        Text(
                            "Sign Out",
                            fontSize = 18.sp, color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

        }
    }

}