package com.jhughes.sandboxapp.remote.rxjava

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jhughes.sandboxapp.remote.pureRetrofit.ApiResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class DataLoader(private val dialogHandler: DialogHandler) : LifecycleObserver {

    private val disposables = CompositeDisposable()

    val state : BehaviorRelay<RequestState> = BehaviorRelay.createDefault(RequestState.IDLE)

    init {
        state.subscribe {
            when(it) {
                RequestState.LOADING -> dialogHandler.showLoader()
                else -> dialogHandler.hideLoader()
            }
        }
    }

    fun <T> request(observable: Observable<T>, observer : DisposableObserver<T>) {
        disposables.add(observable
                .startWith { state.accept(RequestState.LOADING) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { state.accept(RequestState.ERROR)}
                .doOnComplete { state.accept(RequestState.COMPLETE)}
                .subscribeWith(observer))
    }

    fun <T> request(observable: Observable<T>, callback: (apiRequest: ApiResult<T, Throwable>) -> Unit) {
        disposables.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<T>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: T) {
                        callback.invoke(ApiResult(t, null))
                    }

                    override fun onError(e: Throwable) {
                        callback.invoke(ApiResult(null, e))
                    }
                })
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun dispose() {
        disposables.clear()
    }
}