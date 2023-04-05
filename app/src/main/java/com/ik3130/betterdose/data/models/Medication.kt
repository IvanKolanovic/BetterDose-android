package com.ik3130.betterdose.data.models

data class Medication(
    val sponsorName: String,
    val openFda: OpenFDA,
    val products: List<Product>,
)
