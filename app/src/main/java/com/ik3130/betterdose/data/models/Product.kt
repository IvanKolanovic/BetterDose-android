package com.ik3130.betterdose.data.models

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("manufacturer_name")
    val brandName: String,
    @SerializedName("active_ingredients")
    val activeIngredients: List<Ingredients>,
    @SerializedName("dosage_form")
    val dosageForm: String,
    val route: String,
    @SerializedName("marketing_status")
    val marketingStatus: String,
)
