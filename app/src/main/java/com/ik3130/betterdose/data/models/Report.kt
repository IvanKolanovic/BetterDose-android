package com.ik3130.betterdose.data.models

import java.time.LocalDateTime

data class Report(
    val id: String,
    val userId: String,
    val mood: String,
    val reportedAt: LocalDateTime
)
