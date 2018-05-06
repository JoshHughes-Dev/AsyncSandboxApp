package com.jhughes.sandboxapp.features.rxjava.examples

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import com.jakewharton.rxrelay2.BehaviorRelay
import com.jhughes.sandboxapp.features.rxjava.RxJavaServiceActivity
import com.jhughes.sandboxapp.remote.rxjava.RequestState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_feature.*

class RxJavaRequestStateActivity: RxJavaServiceActivity() {

    private val state: BehaviorRelay<RequestState> = BehaviorRelay.createDefault(RequestState.IDLE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        state.subscribe {
            when(it) {
                RequestState.IDLE -> adapter.crimeCategories.clear()
                RequestState.LOADING -> loading.show()
                RequestState.COMPLETE -> loading.hide()
                RequestState.ERROR -> {
                    loading.hide()
                    txt_error.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun fetchCrimeCategories() {

        state.accept(RequestState.IDLE)

        Observable.just(SystemClock.currentThreadTimeMillis())
                .doOnNext { state.accept(RequestState.LOADING) }
                .observeOn(Schedulers.io())
                .flatMap { dataPoliceApi.getCrimeCategories("2018-03")}
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { state.accept(RequestState.ERROR)}
                .doOnComplete { state.accept(RequestState.COMPLETE)}
                .subscribe(this::handleSuccess, this::handleError)
    }
}