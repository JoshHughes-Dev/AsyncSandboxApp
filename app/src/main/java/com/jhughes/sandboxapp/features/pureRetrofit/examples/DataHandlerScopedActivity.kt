package com.jhughes.sandboxapp.features.pureRetrofit.examples

import android.os.Bundle
import com.jhughes.domain.models.CrimeCategory
import com.jhughes.sandboxapp.remote.pureRetrofit.DataHandlerRegistering
import com.jhughes.sandboxapp.remote.pureRetrofit.DataListener
import com.jhughes.sandboxapp.remote.pureRetrofit.DataLoader
import com.jhughes.sandboxapp.features.pureRetrofit.RegularServiceActivity


/* 7.
* added addition layer on top of previous where a activity scoped handler, handles some more thing.
* again similar to JP use especially in MVVM
* */
class DataHandlerScopedActivity: RegularServiceActivity(), DataHandlerRegistering.DataRequester {

    private val key = "crime_category_scoped_task"
    private val dataLoader = DataLoader(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataLoader.register(CrimeCategoryListener())
    }

    override fun onDestroy() {
        super.onDestroy()
        dataLoader.dispose()
    }

    override fun fetchCrimeCategories() {
        val call = dataPoliceApi.getCrimeCategories("2018-03")
        dataLoader.request(call, key)
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