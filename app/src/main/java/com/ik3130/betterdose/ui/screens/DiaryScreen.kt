package com.ik3130.betterdose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ik3130.betterdose.R
import com.ik3130.betterdose.annotations.AppNavGraph
import com.ik3130.betterdose.data.enums.DiaryType
import com.ik3130.betterdose.firebase.AuthViewModel
import com.ik3130.betterdose.ui.components.CustomRating
import com.ik3130.betterdose.ui.screens.destinations.DiaryDetailScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@AppNavGraph(start = true)
@Destination
@Composable
fun DiaryScreen(navigator: DestinationsNavigator, authViewModel: AuthViewModel) {

    LaunchedEffect(Unit) {
        authViewModel.fetchReports()
        authViewModel.fetchDiaries()
    }

    @Composable
    fun DiaryInfo() {
        Spacer(modifier = Modifier.size(20.dp))
        Button(
            onClick = {
                navigator.navigate(
                    DiaryDetailScreenDestination(
                        DiaryDetailScreenDestination.NavArgs(
                            topBarTitle = "Medical diary", diaryType = DiaryType.MEDICINE
                        )
                    ),
                )
            }, modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
        ) {
            Text(
                "Medical diary", color = MaterialTheme.colorScheme.onPrimary, fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
        Button(
            onClick = {
                navigator.navigate(
                    DiaryDetailScreenDestination(
                        DiaryDetailScreenDestination.NavArgs(
                            topBarTitle = "Mood diary", diaryType = DiaryType.MOOD
                        )
                    ),
                )
            }, modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
        ) {
            Text(
                "Mood diary", color = MaterialTheme.colorScheme.onPrimary, fontSize = 18.sp
            )
        }
    }


    Column {
        TopAppBar(backgroundColor = MaterialTheme.colorScheme.surface,
            title = { Text(text = "Medical diary", color = MaterialTheme.colorScheme.onSurface) })

        Column(
            Modifier
                .fillMaxSize()
                .padding(30.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "How are you feeling?",
                fontSize = 30.sp,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                CustomRating(
                    text = "Awful", resDrawableId = R.drawable.awful, authViewModel = authViewModel
                )
                CustomRating(
                    text = "Bad", resDrawableId = R.drawable.bad, authViewModel = authViewModel
                )
                CustomRating(
                    text = "Meh", resDrawableId = R.drawable.meh, authViewModel = authViewModel
                )
                CustomRating(
                    text = "Good", resDrawableId = R.drawable.good, authViewModel = authViewModel
                )
                CustomRating(
                    text = "Great",
                    resDrawableId = R.drawable.awesome,
                    authViewModel = authViewModel
                )
            }

            DiaryInfo()

            Spacer(modifier = Modifier.size(50.dp))

            Image(
                painter = painterResource(id = R.drawable.doctors),
                contentDescription = "BetterDose Logo",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .padding(top = 10.dp)
            )
        }
    }
}