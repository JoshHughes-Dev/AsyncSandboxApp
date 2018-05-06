package com.jhughes.sandboxapp.remote.coolbeans

import retrofit2.Call

typealias DataHandlerResult<T> = (apiResult: ApiResult<T?, Throwable?>) -> Unit

class ApiRequest<T> {

    val call: Call<T>
    val responseCallback: DataHandlerResult<T>
    internal var config: Config = Config()
    var listeners: MutableList<OnRequestStateListener> = ArrayList()

    var state: RequestState = RequestState.Idle
        set(value) {
            field = value
            listeners.forEach { it.onRequestState(value) }
        }

    private var isCancelled: Boolean = false

    constructor(call: Call<T>, responseCallback: DataHandlerResult<T>) : this(
            call, responseCallback, Config(), ArrayList())

    internal constructor(apiRequest: ApiRequest<T>) : this(
            apiRequest.call.clone(),
            apiRequest.responseCallback,
            apiRequest.config,
            apiRequest.listeners)

    private constructor (call: Call<T>,
                         responseCallback: DataHandlerResult<T>,
                         config: Config,
                         listeners: MutableList<OnRequestStateListener>) {
        this.call = call
        this.responseCallback = responseCallback
        this.config = config
        this.listeners = listeners
    }

    fun execute(): ApiResult<T?, Throwable?> {
        return try {
            val response = call.execute()
            ApiResult(response.body(), null)
        } catch (e: Throwable) {
            if (isCancelled) {
                ApiResult(null, null)
            } else {
                ApiResult(null, e)
            }
        }
    }

    fun cancel() {
        isCancelled = true
        call.cancel()
    }

    interface OnRequestStateListener {
        fun onRequestState(requestState: RequestState)
    }
}

class Config(var showLoader: Boolean, var showError: Boolean, var retryable: Boolean, var cancelable: Boolean) {
    constructor() : this(false, false, false, false)
}

sealed class RequestState {
    object Idle : RequestState()
    object Loading : RequestState()
    object Complete : RequestState()
    data class Error(val error: Throwable) : RequestState()
}

fun <T> ApiRequest<T>.clone(): ApiRequest<T> {
    return ApiRequest(this)
}

fun <T> ApiRequest<T>.showLoading(): ApiRequest<T> {
    this.config.showLoader = true
    return this
}

fun <T> ApiRequest<T>.showError(): ApiRequest<T> {
    this.config.showError = true
    return this
}

fun <T> ApiRequest<T>.retryable(): ApiRequest<T> {
    this.config.retryable = true
    return this
}

fun <T> ApiRequest<T>.cancelable(): ApiRequest<T> {
    this.config.cancelable = true
    return this
}