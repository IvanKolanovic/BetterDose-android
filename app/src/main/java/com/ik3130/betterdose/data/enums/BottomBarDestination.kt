package com.ik3130.betterdose.data.enums

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.ik3130.betterdose.R
import com.ik3130.betterdose.ui.screens.destinations.DiaryScreenDestination
import com.ik3130.betterdose.ui.screens.destinations.ProfileScreenDestination
import com.ik3130.betterdose.ui.screens.destinations.SearchScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec, val icon: ImageVector, @StringRes val label: Int
) {
    DiaryNavItem(DiaryScreenDestination, Icons.Default.Home, R.string.diary_scren),
    SearchNavItem(SearchScreenDestination, Icons.Default.Search, R.string.search_screen),
    ProfileNavItem(ProfileScreenDestination, Icons.Default.Settings, R.string.profile_screen),
}