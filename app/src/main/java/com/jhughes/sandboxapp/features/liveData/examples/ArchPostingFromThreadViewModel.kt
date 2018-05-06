package com.jhughes.sandboxapp.features.liveData.examples

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jhughes.domain.models.CrimeCategory
import com.jhughes.sandboxapp.remote.service.DataPoliceServiceFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ArchPostingFromThreadViewModel : ViewModel() {

    private val disposables = CompositeDisposable()

    private val dataPoliceApi = DataPoliceServiceFactory.makeDataPoliceRxJavaService(true)

    val crimeCategories : MutableLiveData<List<CrimeCategory>?> = MutableLiveData()
    val error : MutableLiveData<Throwable?> = MutableLiveData()
    val loadingState: MutableLiveData<Boolean> = MutableLiveData()

    init {
        loadingState.postValue(false)
    }

    fun fetchCrimeCategories() {
        loadingState.postValue(true)
        crimeCategories.postValue(null)

        disposables.add(
                dataPoliceApi.getCrimeCategories("2018-03")
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(object : DisposableObserver<List<CrimeCategory>>() {
                            override fun onComplete() {}
                            override fun onNext(t: List<CrimeCategory>) = handleResponse(t, null)
                            override fun onError(e: Throwable) = handleResponse(null, e)
                        })
        )
    }

    private fun handleResponse(data : List<CrimeCategory>?, e: Throwable?) {
        loadingState.postValue(false)
        crimeCategories.postValue(data)
        error.postValue(e)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}