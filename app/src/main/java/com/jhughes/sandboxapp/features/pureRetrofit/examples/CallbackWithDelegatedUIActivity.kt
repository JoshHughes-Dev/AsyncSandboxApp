package com.jhughes.sandboxapp.features.pureRetrofit.examples

import android.app.ProgressDialog
import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import com.jhughes.domain.models.CrimeCategory
import com.jhughes.sandboxapp.remote.pureRetrofit.DataHandlerResult
import com.jhughes.sandboxapp.remote.pureRetrofit.DataHandlerWithDelegation
import com.jhughes.sandboxapp.remote.pureRetrofit.UiPresenter
import com.jhughes.sandboxapp.features.pureRetrofit.RegularServiceActivity
import retrofit2.Call

class CallbackWithDelegatedUIActivity: RegularServiceActivity(), UiPresenter  {

    private val dataHandler = DataHandlerWithDelegation.instance

    private var activeCall: Call<List<CrimeCategory>>? = null

    private var loadingDialog : ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataHandler.uiPresenter = this
        if(activeCall?.isExecuted == true) {
            hideLoading()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dataHandler.uiPresenter = null
    }

    override fun fetchCrimeCategories() {
        activeCall = dataPoliceApi.getCrimeCategories("2018-03")

        activeCall?.let {
            dataHandler.request(it, callbackHandler, true)
        }
    }

    override fun showLoading() {

        val hasLoadingShowing = loadingDialog != null && loadingDialog!!.isShowing

        val canShow = !hasLoadingShowing
                && lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)

        if(canShow) {
            loadingDialog = ProgressDialog.show(this, "loading", "crimeStuff")
        }
    }

    override fun hideLoading() {
        if(lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
            loadingDialog?.hide()
        }
    }

    private val callbackHandler: DataHandlerResult<List<CrimeCategory>> = {
        when {
            it.data != null -> handleSuccess(it.data)
            it.error != null -> handleError(it.error)
        }
    }
}