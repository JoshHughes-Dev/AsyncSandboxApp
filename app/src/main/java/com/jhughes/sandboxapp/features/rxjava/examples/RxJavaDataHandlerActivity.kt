package com.jhughes.sandboxapp.features.rxjava.examples

import android.os.Bundle
import com.jhughes.sandboxapp.features.rxjava.RxJavaServiceActivity
import com.jhughes.sandboxapp.remote.rxjava.DataLoader
import com.jhughes.sandboxapp.remote.rxjava.DialogHandler

class RxJavaDataHandlerActivity : RxJavaServiceActivity() {

    private val dataLoader = DataLoader(DialogHandler(this, this))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(dataLoader)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(dataLoader)
    }

    override fun fetchCrimeCategories() {
        dataLoader.request(dataPoliceApi.getCrimeCategories("2018-03"), { apiResult ->
            when {
                apiResult.data != null -> handleSuccess(apiResult.data)
                apiResult.error != null -> handleError(apiResult.error)
            }
        })
    }
}