package com.jhughes.sandboxapp.features.pureRetrofit.examples

import android.os.Handler
import com.jhughes.sandboxapp.features.pureRetrofit.RegularServiceActivity
import java.util.concurrent.Executors

/* 4.
* manually execute on background thread and post results on main thread.
* all handled withing activity, not great
* */
class ManualHandlerSimpleActivity: RegularServiceActivity() {

    val handler = Handler()
    val executor = Executors.newCachedThreadPool()

    override fun fetchCrimeCategories() {
        val call = dataPoliceApi.getCrimeCategories("2018-03")

        executor.execute({
           try {
               val response = call.execute()
               handler.post({
                   handleSuccess(response.body()!!)
               })
           } catch (e: Throwable) {
                handler.post({
                    handleError(e)
                })
           }
        })
    }
}