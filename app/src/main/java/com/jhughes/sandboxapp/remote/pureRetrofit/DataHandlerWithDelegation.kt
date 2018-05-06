package com.jhughes.sandboxapp.remote.pureRetrofit

import android.arch.lifecycle.LifecycleObserver
import android.os.Handler
import retrofit2.Call
import java.lang.ref.WeakReference
import java.util.concurrent.Executors

class DataHandlerWithDelegation : LifecycleObserver {

    private val handler = Handler()
    private val executor = Executors.newCachedThreadPool()

    var uiPresenter: UiPresenter? = null

    fun <T> request(call: Call<T>, dataHandlerResult: DataHandlerResult<T>, includeLoading: Boolean = false) {

        val weakRef = WeakReference(dataHandlerResult)

        executor.execute {
            if(includeLoading) {
                handler.post {
                   uiPresenter?.showLoading()
                }
            }
            val apiResult = try {
                val response = call.execute()
                ApiResult(response.body(), null)
            } catch (e: Throwable) {
                ApiResult(null, e)
            }

            handler.post({
                weakRef.get()?.invoke(apiResult)
            })
            if(includeLoading) {
                handler.post {
                    uiPresenter?.hideLoading()
                }
            }
        }
    }

    private object Holder {
        val INSTANCE = DataHandlerWithDelegation()
    }

    companion object Factory {
        val instance: DataHandlerWithDelegation by lazy { Holder.INSTANCE }
    }
}