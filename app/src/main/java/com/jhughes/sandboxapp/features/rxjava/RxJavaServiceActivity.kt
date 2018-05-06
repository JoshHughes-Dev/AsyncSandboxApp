package com.jhughes.sandboxapp.features.rxjava

import com.jhughes.sandboxapp.remote.service.DataPoliceServiceFactory
import com.jhughes.sandboxapp.ui.BaseActivity

abstract class RxJavaServiceActivity: BaseActivity() {

    internal val dataPoliceApi = DataPoliceServiceFactory.makeDataPoliceRxJavaService(true)
}