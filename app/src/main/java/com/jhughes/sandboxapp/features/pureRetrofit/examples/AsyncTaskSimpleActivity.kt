package com.jhughes.sandboxapp.features.pureRetrofit.examples

import android.os.AsyncTask
import android.os.Handler
import com.jhughes.domain.models.CrimeCategory
import com.jhughes.sandboxapp.features.pureRetrofit.RegularServiceActivity
import retrofit2.Call

/* 2.
 * Simple async task to make retrofit sync call.
 * inner non-static class will keep parent class in memory until complete -> leak
 * also leak because it calls activity methods from background thread -> leak
 *
 * have to post results on main thread manually aswell -> booo
 */
class AsyncTaskSimpleActivity: RegularServiceActivity() {

    val handler = Handler()

    override fun fetchCrimeCategories() {
        val call = dataPoliceApi.getCrimeCategories("2018-03")
        SimpleTask().execute(call)
    }

    inner class SimpleTask: AsyncTask<Call<List<CrimeCategory>>, Void, Void?>() {

        override fun doInBackground(vararg p0: Call<List<CrimeCategory>>?): Void? {
            try {
                val call = p0[0]
                val response = call?.execute()
                handler.post({
                    handleSuccess(response!!.body()!!)
                })
            } catch (e: Throwable) {
                handler.post({
                    handleError(e)
                })
            }
            return null
        }
    }
}