package com.jhughes.sandboxapp.remote.pureRetrofit

import android.os.Handler
import retrofit2.Call
import java.util.*
import java.util.concurrent.Executors


class DataHandlerRegistering {

    private val handler = Handler()
    private val executor = Executors.newCachedThreadPool()

    private val map: MutableMap<DataRequester, MutableList<DataListener<*>>> = WeakHashMap()

    fun <T> register(dataRequester: DataRequester, dataListener: DataListener<T>) {
        val listeners = map.getOrPut(dataRequester, { ArrayList() })

        if (listeners.contains(dataListener)) {
            listeners.remove(dataListener)
        }

        listeners.add(dataListener)
    }

    fun unregister(dataRequester: DataRequester) {
        map.remove(dataRequester)
    }

    fun <T> request(dataRequester: DataRequester, call: Call<T>, key: String) {

        val dataListener = map[dataRequester]?.find { it.key == key } as? DataListener<T>

        dataListener?.let {
            executor.execute({
                try {
                    val response = call.execute()
                    handler.post({
                        dataListener.onResponse(response.body()!!)
                    })
                } catch (e: Throwable) {
                    handler.post({
                        dataListener.onError(e)
                    })
                }
            })
        }
    }

    private object Holder {
        val INSTANCE = DataHandlerRegistering()
    }

    interface DataRequester

    companion object Factory {
        val instance: DataHandlerRegistering by lazy { Holder.INSTANCE }
    }
}