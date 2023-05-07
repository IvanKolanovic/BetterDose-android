package com.ik3130.betterdose.data.models

import com.google.gson.annotations.SerializedName

data class OpenFDA(
    @SerializedName("manufacturer_name")
    var manufacturerName: List<String?> = listOf(),
    @SerializedName("brand_name")
    var brandName: List<String?> = listOf(),
    @SerializedName("product_type")
    var productType: List<String?> = listOf(),
    var route: List<String?> = listOf(),
    @SerializedName("substance_name")
    var substanceName: List<String>,
)
