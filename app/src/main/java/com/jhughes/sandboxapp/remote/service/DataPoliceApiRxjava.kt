package com.jhughes.sandboxapp.remote.service

import com.jhughes.domain.models.CrimeCategory
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface DataPoliceApiRxjava {

    @GET("crime-categories")
    fun getCrimeCategories(@Query("date") date : String) : Observable<List<CrimeCategory>>
}