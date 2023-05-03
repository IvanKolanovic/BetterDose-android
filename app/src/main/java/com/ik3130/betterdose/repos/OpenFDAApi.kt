package com.ik3130.betterdose.repos

import com.ik3130.betterdose.data.models.Results
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenFDAApi {
    @GET("drug/drugsfda.json")
    suspend fun getDrugByName(@Query("search") searchQuery: String): Response<Results>
}