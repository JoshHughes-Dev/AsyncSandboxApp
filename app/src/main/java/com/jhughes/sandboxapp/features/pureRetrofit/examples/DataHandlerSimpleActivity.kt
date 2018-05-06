package com.jhughes.sandboxapp.features.pureRetrofit.examples

import com.jhughes.sandboxapp.remote.pureRetrofit.DataHandlerSimple
import com.jhughes.sandboxapp.features.pureRetrofit.RegularServiceActivity

/* 5/
* simple use of a data handler singleton to execute outside of activity scope
* callback functions passed which have reference in here (activity) ??? -> leak
* */
class DataHandlerSimpleActivity: RegularServiceActivity() {

    private val dataHandler = DataHandlerSimple.instance

    override fun fetchCrimeCategories() {
        dataHandler.request(dataPoliceApi.getCrimeCategories("2018-03"), { response ->
            handleSuccess(response)
        }, { t ->
            handleError(t)
        })
    }
}