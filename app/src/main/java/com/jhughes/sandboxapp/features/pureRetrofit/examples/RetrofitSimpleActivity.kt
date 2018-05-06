package com.jhughes.sandboxapp.features.pureRetrofit.examples

import com.jhughes.domain.models.CrimeCategory
import com.jhughes.sandboxapp.features.pureRetrofit.RegularServiceActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* 1.
* Simple call using retrofit async enqueue method (on seperate thread)
* means retrofit will hold activity in memory even if system has destory it -> leak
* */
class RetrofitSimpleActivity: RegularServiceActivity(), Callback<List<CrimeCategory>> {

    override fun fetchCrimeCategories() {
        val call = dataPoliceApi.getCrimeCategories("2018-03")

        call.enqueue(this)
    }

    override fun onFailure(call: Call<List<CrimeCategory>>?, t: Throwable?) {
        t?.let { handleError(t) }
    }

    override fun onResponse(call: Call<List<CrimeCategory>>?, response: Response<List<CrimeCategory>>?) {
        response?.body()?.let { handleSuccess(it) }
    }
}