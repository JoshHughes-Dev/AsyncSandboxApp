package com.jhughes.sandboxapp.features.pureRetrofit.examples

import com.jhughes.domain.models.CrimeCategory
import com.jhughes.sandboxapp.remote.pureRetrofit.DataHandlerCallbacking
import com.jhughes.sandboxapp.remote.pureRetrofit.DataHandlerResult
import com.jhughes.sandboxapp.features.pureRetrofit.RegularServiceActivity
import kotlinx.android.synthetic.main.activity_feature.*

/* 8.
* callback style approach lifted from JP iOS.
* emulating swift [weak self] with weak reference inside datahandler
* only issue is that i have to keep a strong ref to callbackhandler in activity
* */
class CallbackOnlyActivity: RegularServiceActivity() {

    private val dataHandler = DataHandlerCallbacking.instance

    override fun fetchCrimeCategories() {
        loading.show()
        val call = dataPoliceApi.getCrimeCategories("2018-03")
        dataHandler.request(call, callbackHandler)
    }

    private val callbackHandler : DataHandlerResult<List<CrimeCategory>> = {
        loading.hide()
        when {
            it.data != null -> handleSuccess(it.data)
            it.error != null -> handleError(it.error)
        }
    }
}