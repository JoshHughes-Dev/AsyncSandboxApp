package com.jhughes.sandboxapp.remote.coolbeans

import android.app.AlertDialog
import android.app.ProgressDialog
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.content.Context

class DialogHandler(private val context: Context,
                    private val lifecycleOwner: LifecycleOwner) {

    private val loadingDialog: ProgressDialog by lazy {
        ProgressDialog.show(context, "loading", "crime stuff")
    }

    fun showLoader(apiRequest: ApiRequest<*>?) {
        val validLifecycleState = lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        if (!validLifecycleState) {
            return
        }

        loadingDialog.show()
        if (apiRequest?.config?.cancelable == true) {
            loadingDialog.setCancelable(true)
            loadingDialog.setOnCancelListener {
                apiRequest.cancel()
            }
        } else {
            loadingDialog.setCancelable(false)
            loadingDialog.setOnCancelListener {}
        }
    }

    fun hideLoader() {
        val validLifecycleState = lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        if (!validLifecycleState) {
            return
        }

        loadingDialog.hide()
    }

    fun showError(apiRequest: ApiRequest<*>?, t: Throwable?, retryCallback: ((apiRequest: ApiRequest<*>) -> Unit)?) {
        val validLifecycleState = lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        if (!validLifecycleState) {
            return
        }

        AlertDialog.Builder(context).apply {
            setTitle("Error")
            setMessage(t?.message)
            if(apiRequest?.config?.retryable == true && retryCallback != null) {
                setPositiveButton("Retry", { _, _ ->
                    retryCallback(apiRequest)
                })
            }
            setNegativeButton("Dismiss", { dialog, _ ->
                dialog.dismiss()
            })
        }.show()
    }

}