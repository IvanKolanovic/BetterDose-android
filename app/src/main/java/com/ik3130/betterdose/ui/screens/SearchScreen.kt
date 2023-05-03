package com.ik3130.betterdose.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ik3130.betterdose.R
import com.ik3130.betterdose.annotations.AppNavGraph
import com.ik3130.betterdose.data.models.Diary
import com.ik3130.betterdose.data.models.Medication
import com.ik3130.betterdose.firebase.AuthViewModel
import com.ik3130.betterdose.ui.Utils
import com.ik3130.betterdose.ui.components.CustomDivider
import com.ik3130.betterdose.ui.components.MedInfoItem
import com.ik3130.betterdose.ui.components.SearchBar
import com.ik3130.betterdose.ui.screens.destinations.DiaryScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.navigate
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@AppNavGraph
@Destination
@Composable
fun SearchScreen(
    navigator: DestinationsNavigator,
    navController: NavController,
    authViewModel: AuthViewModel
) {

    val textState = remember { mutableStateOf(TextFieldValue("")) }
    val date = remember { mutableStateOf(LocalDate.now()) }
    val time = remember { mutableStateOf(LocalTime.now()) }

    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SearchBar(authViewModel = authViewModel, state = textState)
        if (authViewModel.medication != null) MedView(
            navController,
            authViewModel, authViewModel.medication!!, dateDialogState, timeDialogState, time, date
        )
        else DefaultView()
    }
}

@Composable
fun DefaultView() {
    Spacer(modifier = Modifier.size(400.dp))
    Image(
        painter = painterResource(id = R.drawable.doctors),
        contentDescription = "BetterDose Logo",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    )
}


@Composable
fun MedView(
    navController: NavController,
    authViewModel: AuthViewModel,
    medication: Medication,
    dateDialogState: MaterialDialogState,
    timeDialogState: MaterialDialogState,
    timeState: MutableState<LocalTime>,
    dateState: MutableState<LocalDate>
) {
    Text(
        text = medication.openFda.brandName[0],
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(top = 10.dp)
    )
    CustomDivider(width = 325.dp, height = 1.dp, color = MaterialTheme.colorScheme.primary)
    Text(
        text = "Medication data",
        fontSize = 20.sp,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(top = 20.dp, bottom = 10.dp)
    )

    MedInfoItem(
        labelName = "Brand name: ",
        medInfo = medication.openFda.brandName[0],
        modifier = Modifier.padding(horizontal = 15.dp)
    )
    MedInfoItem(
        labelName = "Substance: ",
        medInfo = medication.openFda.substanceName[0],
        modifier = Modifier.padding(horizontal = 15.dp)
    )
    MedInfoItem(
        labelName = "Manufacturer: ",
        medInfo = medication.openFda.manufacturerName[0],
        modifier = Modifier.padding(horizontal = 15.dp)
    )
    MedInfoItem(
        labelName = "Product type: ",
        medInfo = medication.openFda.productType[0],
        modifier = Modifier.padding(horizontal = 15.dp)
    )
    MedInfoItem(
        labelName = "Dosage form: ",
        medInfo = medication.products[0].dosageForm,
        modifier = Modifier.padding(horizontal = 15.dp)
    )
    MedInfoItem(
        labelName = "Route: ",
        medInfo = medication.products[0].route,
        modifier = Modifier.padding(horizontal = 15.dp)
    )
    MedInfoItem(
        labelName = "Marketing status: ",
        medInfo = medication.products[0].marketingStatus,
        modifier = Modifier.padding(horizontal = 15.dp)
    )
    Button(onClick = { dateDialogState.show() }, modifier = Modifier.padding(top = 20.dp)) {
        androidx.compose.material3.Text("Add to Diary")
    }
    Image(
        painter = painterResource(id = R.drawable.doctors),
        contentDescription = "BetterDose Logo",
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
    MaterialDialog(dialogState = dateDialogState, buttons = {
        positiveButton("Ok")
        negativeButton("Cancel")
    }) {
        datepicker { date ->
            dateState.value = date
            timeDialogState.show()
        }
    }

    MaterialDialog(dialogState = timeDialogState, buttons = {
        positiveButton("Ok")
        negativeButton("Cancel")
    }) {
        timepicker { time ->
            timeState.value = time
            val dateTime: LocalDateTime = LocalDateTime.of(dateState.value, timeState.value)
            val diary = Diary(
                drugName = medication.openFda.substanceName[0],
                userId = authViewModel.currentUser!!.uid,
                takeAt = Utils.TIME_FORMATTER.format(dateTime)
            )
            authViewModel.createDiaryEntry(request = diary)
            navController.navigate(DiaryScreenDestination.invoke())
        }
    }
}