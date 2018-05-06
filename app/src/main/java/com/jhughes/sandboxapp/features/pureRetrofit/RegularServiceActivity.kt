package com.jhughes.sandboxapp.features.pureRetrofit

import com.jhughes.sandboxapp.remote.service.DataPoliceServiceFactory
import com.jhughes.sandboxapp.ui.BaseActivity

abstract class RegularServiceActivity: BaseActivity() {

    internal val dataPoliceApi = DataPoliceServiceFactory.makeDataPoliceService(true)
}