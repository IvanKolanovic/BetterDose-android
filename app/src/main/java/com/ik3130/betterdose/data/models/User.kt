package com.ik3130.betterdose.data.models

import java.time.LocalDateTime

data class User(
    val id: String,
    val email: String,
    val fullName: String,
    val createdAt: LocalDateTime
)
