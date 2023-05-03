package com.ik3130.betterdose.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.ik3130.betterdose.data.enums.BottomBarDestination
import com.ik3130.betterdose.firebase.AuthViewModel
import com.ik3130.betterdose.ui.screens.startAppDestination
import com.ramcosta.composedestinations.navigation.navigate

@Composable
fun CustomBottomBar(
    authViewModel: AuthViewModel, navController: NavController
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.onTertiary,
    ) {
        BottomBarDestination.values().forEach { destination ->
            BottomNavigationItem(
                selected = authViewModel.currentDestination == destination.direction,
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.secondary,
                onClick = {
                    navController.navigate(destination.direction, fun NavOptionsBuilder.() {
                        authViewModel.setCurrentDestination(destination.direction.startAppDestination)
                        launchSingleTop = true
                    })
                },
                icon = {
                    Icon(
                        destination.icon, contentDescription = stringResource(destination.label)
                    )
                },
                label = { Text(stringResource(destination.label)) },
            )
        }
    }
}

