package com.ik3130.betterdose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.ik3130.betterdose.annotations.AppNavGraph
import com.ik3130.betterdose.data.enums.DiaryType
import com.ik3130.betterdose.firebase.AuthViewModel
import com.ik3130.betterdose.ui.components.DiaryDetailRow
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@AppNavGraph
@Destination
@Composable
fun DiaryDetailScreen(
    navigator: DestinationsNavigator,
    authViewModel: AuthViewModel,
    topBarTitle: String,
    diaryType: DiaryType
) {

    Column {
        TopAppBar(backgroundColor = MaterialTheme.colorScheme.surface,
            title = { androidx.compose.material.Text(text = topBarTitle, color = MaterialTheme.colorScheme.onSurface) },
            navigationIcon = {
                IconButton(onClick = {
                    navigator.popBackStack()
                }) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back icon", tint =MaterialTheme.colorScheme.scrim )
                }
            })

        if (DiaryType.MOOD == diaryType) LazyColumn {
            items(items = authViewModel.moodsList, key = { arg -> arg.id }) { arg ->
                DiaryDetailRow(text = "Felt ${arg.mood} at ${arg.reportedAt}",
                    onDelete = { authViewModel.deleteDoc("Report", arg.id) })
            }
        }
        else LazyColumn {
            items(items = authViewModel.diaryList, key = { arg -> arg.id }) { arg ->
                DiaryDetailRow(text = "Take '${arg.drugName}' at ${arg.takeAt}",
                    onDelete = { authViewModel.deleteDoc("Diary", arg.id) })
            }
        }
    }
}