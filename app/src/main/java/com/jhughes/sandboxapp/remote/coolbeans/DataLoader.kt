package com.jhughes.sandboxapp.remote.coolbeans

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent

class DataLoader(private val dialogHandler: DialogHandler) : LifecycleObserver {

    private val dataHandler = DataHandler.instance

    private val apiRequests: MutableList<ApiRequest<*>> = ArrayList()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        val activeRequests = apiRequests.filter { apiRequest -> !apiRequest.call.isExecuted }
        if(activeRequests.isEmpty()){
            dialogHandler.hideLoader()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun dispose() {
        apiRequests.clear()
    }

    fun <T> request(apiRequest: ApiRequest<T>) {
        apiRequests.add(apiRequest)

        apiRequest.listeners.add(createListener(apiRequest))

        dataHandler.request(apiRequest)
    }

    private fun <T> createListener(apiRequest: ApiRequest<T>): ApiRequest.OnRequestStateListener {
        return object : ApiRequest.OnRequestStateListener {
            override fun onRequestState(requestState: RequestState) {
                when (requestState) {
                    is RequestState.Idle -> handleIdleState(apiRequest)
                    is RequestState.Loading -> handleLoadingState(apiRequest)
                    is RequestState.Error -> handleErrorState(apiRequest, requestState)
                    is RequestState.Complete -> handleCompleteState(apiRequest)
                }
            }
        }
    }

    private fun handleIdleState(apiRequest : ApiRequest<*>) {
        if (apiRequest.config.showLoader) {
            dialogHandler.hideLoader()
        }
    }

    private fun handleLoadingState(apiRequest : ApiRequest<*>) {
        if (apiRequest.config.showLoader) {
            dialogHandler.showLoader(apiRequest)
        }
    }

    private fun handleErrorState(apiRequest : ApiRequest<*>, requestState: RequestState.Error) {
        if (apiRequest.config.showLoader) {
            dialogHandler.hideLoader()
        }
        if (apiRequest.config.showError) {
            dialogHandler.showError(apiRequest, requestState.error, {
                request(it)
            })
        }
        apiRequests.remove(apiRequest)
    }

    private fun handleCompleteState(apiRequest : ApiRequest<*>) {
        if (apiRequest.config.showLoader) {
            dialogHandler.hideLoader()
        }
        apiRequests.remove(apiRequest)
    }
}