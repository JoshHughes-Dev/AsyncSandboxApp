package com.jhughes.sandboxapp.remote.service

import com.jhughes.domain.models.CrimeCategory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DataPoliceApi {

    @GET("crime-categories")
    fun getCrimeCategories(@Query("date") date : String) : Call<List<CrimeCategory>>
}