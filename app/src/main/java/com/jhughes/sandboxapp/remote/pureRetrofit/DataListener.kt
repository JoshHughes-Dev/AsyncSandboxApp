package com.jhughes.sandboxapp.remote.pureRetrofit

abstract class DataListener<in T>(val key: String) {
    abstract fun onResponse(data: T)
    abstract fun onError(t: Throwable)
}