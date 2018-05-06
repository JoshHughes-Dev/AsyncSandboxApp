package com.jhughes.sandboxapp.remote.rxjava

import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApiRequest<T>(private val request: Observable<T>) {
    val state : BehaviorRelay<RequestState> = BehaviorRelay.createDefault(RequestState.IDLE)
    val response: BehaviorRelay<T> = BehaviorRelay.create<T>()
    val error : BehaviorRelay<Throwable> = BehaviorRelay.create<Throwable>()

    private val trigger = BehaviorRelay.create<Long>()

    init {
        trigger.doOnNext { state.accept(RequestState.LOADING) }
                .observeOn(Schedulers.io())
                .flatMap { request }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { state.accept(RequestState.ERROR) }
                .doOnError(error)
                .onErrorResumeNext(Observable.empty())
                .doOnNext { state.accept(RequestState.COMPLETE) }
                .subscribe(response)
    }

    fun execute() {
        trigger.accept(System.currentTimeMillis())
    }
}