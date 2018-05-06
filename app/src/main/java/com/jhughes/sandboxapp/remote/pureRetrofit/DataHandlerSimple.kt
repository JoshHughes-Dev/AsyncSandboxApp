package com.jhughes.sandboxapp.remote.pureRetrofit

import android.os.Handler
import retrofit2.Call
import java.util.concurrent.Executors

class DataHandlerSimple {

    private val handler = Handler()
    private val executor = Executors.newCachedThreadPool()

    fun <T> request(call: Call<T>, success: (response: T) -> Unit, failure: (t: Throwable) -> Unit) {

        executor.execute({
            try {
                val response = call.execute()
                handler.post({
                    success(response.body()!!)
                })
            } catch (e: Throwable) {
                handler.post({
                    failure(e)
                })
            }
        })
    }

    private object Holder {
        val INSTANCE = DataHandlerSimple()
    }

    companion object Factory{
        val instance: DataHandlerSimple by lazy { Holder.INSTANCE }
    }
}