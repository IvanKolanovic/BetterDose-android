package com.ik3130.betterdose.data.models

import java.time.LocalDateTime

data class Diary(
    val id: String,
    val drugName: String,
    val userId: String,
    val takeAt: LocalDateTime
)
