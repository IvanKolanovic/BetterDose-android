package com.ik3130.betterdose.data.models

import com.google.gson.annotations.SerializedName

data class Medication(
    @SerializedName("sponsor_name")
    val sponsorName: String,
    @SerializedName("openfda")
    val openFda: OpenFDA,
    val products: List<Product>,
)
