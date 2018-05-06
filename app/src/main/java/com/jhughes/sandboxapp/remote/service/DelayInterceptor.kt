package com.jhughes.sandboxapp.remote.service

import okhttp3.Interceptor
import okhttp3.Response

class DelayInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        Thread.sleep(5000)
        return chain!!.proceed(chain.request())
    }
}