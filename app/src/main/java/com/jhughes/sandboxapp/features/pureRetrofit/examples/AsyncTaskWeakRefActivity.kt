package com.jhughes.sandboxapp.features.pureRetrofit.examples

import android.os.AsyncTask
import com.jhughes.domain.models.CrimeCategory
import com.jhughes.sandboxapp.features.pureRetrofit.RegularServiceActivity
import retrofit2.Call
import retrofit2.Response
import java.lang.ref.WeakReference

/* 3.
 * async task to make retrofit sync call with weaf ref to activity.
 * inner non-static class will keep parent class in memory until complete -> leak
 * resolves activity reference using weak ref.
 *
 * why we shouldnt use asynctask:
 * https://medium.com/@ali.muzaffar/handlerthreads-and-why-you-should-be-using-them-in-your-android-apps-dc8bf1540341
 */
class AsyncTaskWeakRefActivity: RegularServiceActivity() {

    override fun fetchCrimeCategories() {
        val call = dataPoliceApi.getCrimeCategories("2018-03")

        SimpleTaskWithWeakRef(this).execute(call)
    }

    inner class SimpleTaskWithWeakRef: AsyncTask<Call<List<CrimeCategory>>, Void, Response<List<CrimeCategory>>> {

        private val weakRef: WeakReference<AsyncTaskWeakRefActivity>

        constructor(activity: AsyncTaskWeakRefActivity) {
            weakRef = WeakReference(activity)
        }

        override fun doInBackground(vararg p0: Call<List<CrimeCategory>>?): Response<List<CrimeCategory>>? {
            return try {
                val call = p0[0]
                call?.execute()
            } catch (e: Throwable) {
                null
            }
        }

        override fun onPostExecute(result: Response<List<CrimeCategory>>?) {
            super.onPostExecute(result)
            weakRef.get()?.let {
                try {
                    it.handleSuccess(result!!.body()!!)
                } catch (e: Throwable) {
                    it.handleError(e)
                }
            }
        }
    }
}