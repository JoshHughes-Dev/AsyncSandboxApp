package com.jhughes.sandboxapp.features.rxjava.examples

import com.jhughes.sandboxapp.features.rxjava.RxJavaServiceActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/* 1.
* Simple rxjava example
* threading done right but using activity as callback means reference held -> leak
* */
class RxJavaSimpleActivity: RxJavaServiceActivity() {

    override fun fetchCrimeCategories() {
        dataPoliceApi.getCrimeCategories("2018-03")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccess, this::handleError)
    }
}