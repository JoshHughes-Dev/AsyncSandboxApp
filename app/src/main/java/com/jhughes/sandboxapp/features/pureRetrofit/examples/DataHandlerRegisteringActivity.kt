package com.jhughes.sandboxapp.features.pureRetrofit.examples

import android.os.Bundle
import com.jhughes.domain.models.CrimeCategory
import com.jhughes.sandboxapp.remote.pureRetrofit.DataHandlerRegistering
import com.jhughes.sandboxapp.remote.pureRetrofit.DataListener
import com.jhughes.sandboxapp.features.pureRetrofit.RegularServiceActivity

/* 6.
* using inner class as listener, register and unregister manually to singleton manager class
* prevents memory leaks with inner class with explicit de-referencing.
* easy to mess up though + boiler plate code.
* closest to current JP implementation
* */
class DataHandlerRegisteringActivity: RegularServiceActivity(), DataHandlerRegistering.DataRequester {

    private val key = "crime_category_task"

    private val dataHandler = DataHandlerRegistering.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataHandler.register(this, CrimeCategoryListener())
    }

    override fun onDestroy() {
        super.onDestroy()
        dataHandler.unregister(this)
    }

    override fun fetchCrimeCategories() {
        val call = dataPoliceApi.getCrimeCategories("2018-03")
        dataHandler.request(this, call, key)
    }

    inner class CrimeCategoryListener: DataListener<List<CrimeCategory>>(key) {
        override fun onResponse(data: List<CrimeCategory>) {
            handleSuccess(data)
        }

        override fun onError(t: Throwable) {
            handleError(t)
        }
    }
}