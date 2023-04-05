package com.ik3130.betterdose.data.models

data class OpenFDA(
    var manufacturerName: List<String>,
    var brandName: List<String>,
    var productType: List<String>,
    var route: List<String>,
    var substanceName: List<String>,
)
