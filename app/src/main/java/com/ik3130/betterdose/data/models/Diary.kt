package com.ik3130.betterdose.data.models

import java.util.*

data class Diary(
    val id: String = UUID.randomUUID().toString(),
    val drugName: String = "",
    val userId: String = "",
    val takeAt: String = ""
)
