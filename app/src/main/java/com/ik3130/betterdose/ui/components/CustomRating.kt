package com.ik3130.betterdose.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ik3130.betterdose.data.models.Report
import com.ik3130.betterdose.firebase.AuthViewModel
import com.ik3130.betterdose.ui.Utils
import java.time.LocalDateTime

@Composable
fun CustomRating(
    text: String, resDrawableId: Int, authViewModel: AuthViewModel
) {
    val context = LocalContext.current

    Box(Modifier.clickable {
        authViewModel.createReport(
            Report(
                userId = authViewModel.currentUser!!.uid,
                mood = text,
                reportedAt = Utils.TIME_FORMATTER.format(LocalDateTime.now())
            )

        )
        Toast.makeText(context, "Mood recorded.", Toast.LENGTH_SHORT).show()
    }) {
        Image(
            painter = painterResource(id = resDrawableId),
            contentDescription = "BetterDose Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .width(60.dp)
                .height(50.dp)
        )
        Text(
            text = text, modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 45.dp), color = MaterialTheme.colorScheme.scrim
        )
    }

}