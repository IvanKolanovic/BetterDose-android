package com.ik3130.betterdose.data.models

import com.google.gson.annotations.SerializedName

data class OpenFDA(
    @SerializedName("manufacturer_name")
    var manufacturerName: List<String>,
    @SerializedName("brand_name")
    var brandName: List<String>,
    @SerializedName("product_type")
    var productType: List<String>,
    var route: List<String>,
    @SerializedName("substance_name")
    var substanceName: List<String>,
)
