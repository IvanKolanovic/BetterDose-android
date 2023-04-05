package com.ik3130.betterdose.data.models

data class Product(
    val brandName: String,
    val activeIngredients: List<Ingredients>,
    val dosageForm: String,
    val route: String,
    val marketingStatus: String,
)
