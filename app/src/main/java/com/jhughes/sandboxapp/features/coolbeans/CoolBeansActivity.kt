package com.jhughes.sandboxapp.features.coolbeans

import android.os.Bundle
import android.view.View
import com.jhughes.domain.models.CrimeCategory
import com.jhughes.sandboxapp.features.pureRetrofit.RegularServiceActivity
import com.jhughes.sandboxapp.remote.coolbeans.*
import kotlinx.android.synthetic.main.activity_feature.*

class CoolBeansActivity : RegularServiceActivity() {

    private val dialogHandler = DialogHandler(this, this)
    private val dataLoader = DataLoader(dialogHandler)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(dataLoader)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(dataLoader)
    }

    override fun fetchCrimeCategories() {
        val call = dataPoliceApi.getCrimeCategories("2018-03")

        val apiRequest = ApiRequest(call, callbackHandler)
                .showLoading().cancelable().showError().retryable()

//        apiRequest.listeners.add(requestStateListener)

        dataLoader.request(apiRequest)
    }

    private val requestStateListener : ApiRequest.OnRequestStateListener = object : ApiRequest.OnRequestStateListener {
        override fun onRequestState(requestState: RequestState) {
            when(requestState) {
                is RequestState.Idle -> {
                    loading.hide()
                    txt_error.visibility = View.GONE
                }
                is RequestState.Loading -> {
                    loading.show()
                }
                else -> loading.hide()
            }
        }
    }

    private val callbackHandler: DataHandlerResult<List<CrimeCategory>> = {
        when {
            it.data != null -> handleSuccess(it.data)
            it.error != null -> handleError(it.error)
        }
    }
}