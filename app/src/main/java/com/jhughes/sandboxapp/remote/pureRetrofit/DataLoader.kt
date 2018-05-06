package com.jhughes.sandboxapp.remote.pureRetrofit

import retrofit2.Call

class DataLoader(private val dataRequester: DataHandlerRegistering.DataRequester) {

    private val dataHandler = DataHandlerRegistering.instance

    fun register(dataListener: DataListener<*>) {
        dataHandler.register(dataRequester, dataListener)
    }

    fun request(call: Call<*>, key: String) {
        dataHandler.request(dataRequester, call, key)
    }

    fun dispose() {
        dataHandler.unregister(dataRequester)
    }
}