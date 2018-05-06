package com.jhughes.sandboxapp.features.rxjava.examples

import com.jhughes.domain.models.CrimeCategory
import com.jhughes.sandboxapp.features.rxjava.RxJavaServiceActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/* 2.
* Simple example except subsribing with a displosable observer.
 * which we can hold a reference to until activity is destroyed -> no leaks!!!
* */
class RxJavaSimpleDisposedActivity: RxJavaServiceActivity() {

    private lateinit var disposableTask: DisposableObserver<List<CrimeCategory>>

    override fun onDestroy() {
        super.onDestroy()
        disposableTask.dispose()
    }

    override fun fetchCrimeCategories() {
        disposableTask = dataPoliceApi.getCrimeCategories("2018-03")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<CrimeCategory>>() {
                    override fun onComplete() {}
                    override fun onNext(t: List<CrimeCategory>) = handleSuccess(t)
                    override fun onError(e: Throwable) = handleError(e)
                })
    }
}