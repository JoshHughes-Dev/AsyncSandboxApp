package com.jhughes.sandboxapp.features.pureRetrofit.examples

import android.util.Log
import com.jhughes.domain.models.CrimeCategory
import com.jhughes.sandboxapp.features.pureRetrofit.RegularServiceActivity
import com.jhughes.sandboxapp.remote.pureRetrofit.DataHandlerCallbacking
import com.jhughes.sandboxapp.remote.pureRetrofit.DataHandlerResult
import com.jhughes.sandboxapp.remote.pureRetrofit.DataRequest
import com.jhughes.sandboxapp.remote.pureRetrofit.RequestState
import kotlinx.android.synthetic.main.activity_feature.*

/* 9.
* added state handler object.
* similar to last solution but now have to keep a reference to wrapping dataRequest object.
* */
class CallbackWithRequestStateActivity: RegularServiceActivity() {

    private val dataHandler = DataHandlerCallbacking.instance

    private var dataRequest : DataRequest<List<CrimeCategory>>? = null

    override fun fetchCrimeCategories() {
        val call = dataPoliceApi.getCrimeCategories("2018-03")

        dataRequest = DataRequest(call, callbackHandler, stateCallbackHandler)

        dataRequest?.let {
            dataHandler.request(it)
        }
    }

    private val callbackHandler: DataHandlerResult<List<CrimeCategory>> = {
        Log.d("CallbackOnlyActivity", "callback: " + this.isDestroyed)
        when {
            it.data != null -> handleSuccess(it.data)
            it.error != null -> handleError(it.error)
        }
    }

    private val stateCallbackHandler: (requestState: RequestState) -> Unit = {
        when(it) {
            RequestState.IDLE -> loading.hide()
            RequestState.ACTIVE -> loading.show()
        }
    }
}