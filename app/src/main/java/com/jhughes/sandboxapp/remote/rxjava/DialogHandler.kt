package com.jhughes.sandboxapp.remote.rxjava

import android.app.ProgressDialog
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.content.Context

class DialogHandler(private val context: Context,
                    private val lifecycleOwner: LifecycleOwner) {

    private val loadingDialog: ProgressDialog by lazy {
        ProgressDialog.show(context, "loading", "crime stuff")
    }

    fun showLoader() {
        val validLifecycleState = lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        if (!validLifecycleState) {
            return
        }

        loadingDialog.show()
    }

    fun hideLoader() {
        val validLifecycleState = lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        if (!validLifecycleState) {
            return
        }

        loadingDialog.hide()
    }
}