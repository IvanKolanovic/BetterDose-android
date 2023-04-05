package com.ik3130.betterdose.nav

import com.ik3130.betterdose.data.constants.Constants.PROFILE_SCREEN
import com.ik3130.betterdose.data.constants.Constants.SIGN_IN_SCREEN
import com.ik3130.betterdose.data.constants.Constants.SIGN_UP_SCREEN

sealed class Screen(val route: String) {
    object SignInScreen : Screen(SIGN_IN_SCREEN)
    object SignUpScreen : Screen(SIGN_UP_SCREEN)
    object ProfileScreen : Screen(PROFILE_SCREEN)
}