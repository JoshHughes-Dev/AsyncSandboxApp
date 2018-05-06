package com.jhughes.sandboxapp.remote.coolbeans

import java.lang.ref.WeakReference

class DataHandler {

    private val appExecutors = AppExecutors()

    fun <T> request(apiRequest: ApiRequest<T>) {

        val weakRef = WeakReference(apiRequest)

        appExecutors.networkIO().execute {
            appExecutors.mainThread().execute {
                weakRef.get()?.state = RequestState.Loading
            }

            val apiResult = weakRef.get()?.execute()

            apiResult?.let { result ->
                appExecutors.mainThread().execute {
                    weakRef.get()?.responseCallback?.invoke(result)
                    when {
                        result.data != null -> weakRef.get()?.state = RequestState.Complete
                        result.error != null -> weakRef.get()?.state = RequestState.Error(result.error)
                    }
                }
            }
        }
    }

    private object Holder {
        val INSTANCE = DataHandler()
    }

    companion object Factory{
        val instance: DataHandler by lazy { Holder.INSTANCE }
    }
}