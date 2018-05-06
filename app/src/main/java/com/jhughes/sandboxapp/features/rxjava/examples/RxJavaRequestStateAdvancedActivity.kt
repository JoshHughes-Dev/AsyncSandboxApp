package com.jhughes.sandboxapp.features.rxjava.examples

import android.os.Bundle
import android.view.View
import com.jhughes.domain.models.CrimeCategory
import com.jhughes.sandboxapp.features.rxjava.RxJavaServiceActivity
import com.jhughes.sandboxapp.remote.rxjava.ApiRequest
import com.jhughes.sandboxapp.remote.rxjava.RequestState
import kotlinx.android.synthetic.main.activity_feature.*

class RxJavaRequestStateAdvancedActivity: RxJavaServiceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        txt_error.setOnClickListener {
            fetchCrimeCategories()
        }
    }

    override fun fetchCrimeCategories() {
        val apiRequest = ApiRequest(dataPoliceApi.getCrimeCategories("2018-03"))
        apiRequest.subscribeToApiRequest()
        apiRequest.execute()
    }

    private fun ApiRequest<List<CrimeCategory>>.subscribeToApiRequest() {
        this.state.subscribe {
            when (it) {
                RequestState.IDLE -> {
                    adapter.crimeCategories.clear()
                    txt_error.visibility = View.GONE
                }
                RequestState.LOADING -> loading.show()
                RequestState.COMPLETE -> loading.hide()
                RequestState.ERROR -> {
                    loading.hide()
                    txt_error.visibility = View.VISIBLE
                }
            }
        }

        this.response.subscribe { data -> handleSuccess(data) }
        this.error.subscribe { t -> handleError(t) }
    }
}