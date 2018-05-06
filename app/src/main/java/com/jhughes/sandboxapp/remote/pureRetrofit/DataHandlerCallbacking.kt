package com.jhughes.sandboxapp.remote.pureRetrofit

import android.os.Handler
import android.os.Looper
import retrofit2.Call
import java.lang.ref.WeakReference
import java.util.concurrent.Executors

typealias DataHandlerResult<T> = (apiResult: ApiResult<T?, Throwable?>) -> Unit

class DataHandlerCallbacking {

    private val handler = Handler(Looper.getMainLooper())
    private val executor = Executors.newCachedThreadPool()

    fun <T> request(call: Call<T>, dataHandlerResult: DataHandlerResult<T>) {

        val weakRef = WeakReference(dataHandlerResult)

        executor.execute {
            val apiResult = try {
                val response = call.execute()
                ApiResult(response.body(), null)
            } catch (e: Throwable) {
                ApiResult(null, e)
            }

            handler.post({
                weakRef.get()?.invoke(apiResult)
            })
        }
    }

    //hold strong ref outside so that data request doesnt get eaten up by GC
    fun <T> request(dataRequest: DataRequest<T>) {
        val weakRef = WeakReference(dataRequest)

        executor.execute{
            handler.post {
                weakRef.get()?.stateCallback?.invoke(RequestState.ACTIVE)
            }
            val apiResult = try {
                val response = weakRef.get()?.call?.execute()
                ApiResult(response!!.body(), null)
            } catch (e: Throwable) {
                ApiResult(null, e)
            }
            handler.post {
                weakRef.get()?.let {
                    it.stateCallback.invoke(RequestState.IDLE)
                    it.dataHandlerResult.invoke(apiResult)
                }
            }
        }
    }

    private object Holder {
        val INSTANCE = DataHandlerCallbacking()
    }

    companion object Factory {
        val instance: DataHandlerCallbacking by lazy { Holder.INSTANCE }
    }
}

class DataRequest<T>(val call: Call<T>, val dataHandlerResult: DataHandlerResult<T>,
                     val stateCallback: (requestState: RequestState) -> Unit)

enum class RequestState {
    IDLE,
    ACTIVE
}

