package com.ik3130.betterdose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MedInfoItem(labelName: String, medInfo: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = labelName,
                    fontSize = 15.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = medInfo, fontSize = 14.sp, color = MaterialTheme.colorScheme.scrim
                )
            }
            // fixes the horizontal arrangement of the items for some reason
            CustomDivider(width = 340.dp, height = 1.dp, color = Color.Transparent)
        }
    }
}