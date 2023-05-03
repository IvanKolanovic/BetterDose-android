package com.ik3130.betterdose.data.models

import java.util.*

data class Report(
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "000",
    val mood: String="00",
    val reportedAt: String="0000"
)
