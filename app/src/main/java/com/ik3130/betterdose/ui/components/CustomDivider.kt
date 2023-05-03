package com.ik3130.betterdose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun CustomDivider(width: Dp, height: Dp, color: Color) {
    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .background(color = color)
    )

}