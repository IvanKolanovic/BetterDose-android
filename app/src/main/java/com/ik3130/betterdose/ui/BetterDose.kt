package com.ik3130.betterdose.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.ik3130.betterdose.datastore.SaveLocalStore
import com.ik3130.betterdose.firebase.AuthViewModel
import com.ik3130.betterdose.ui.components.CustomBottomBar
import com.ik3130.betterdose.ui.screens.NavGraphs
import com.ik3130.betterdose.ui.screens.OnBoardingScreen
import com.ik3130.betterdose.ui.screens.startAppDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BetterDose(authViewModel: AuthViewModel, navController: NavHostController) {

    val localStore = SaveLocalStore(LocalContext.current)
    val hasSeen = localStore.getUISeenState.collectAsState(initial = false)

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(bottomBar = {
            if (authViewModel.isUserLoggedIn()) CustomBottomBar(authViewModel, navController)
        }) {
            if (authViewModel.isUserLoggedIn()) {
                authViewModel.setCurrentDestination(NavGraphs.app.startAppDestination)
                DestinationsNavHost(navGraph = NavGraphs.app,
                    navController = navController,
                    dependenciesContainerBuilder = {
                        dependency(authViewModel)
                        dependency(navController)
                    })
            } else {
                if (!hasSeen.value) {
                    OnBoardingScreen(authViewModel, localStore)
                } else {
                    DestinationsNavHost(navGraph = NavGraphs.auth,
                        navController = navController,
                        dependenciesContainerBuilder = {
                            dependency(authViewModel)
                        })
                }
            }
        }
    }
}