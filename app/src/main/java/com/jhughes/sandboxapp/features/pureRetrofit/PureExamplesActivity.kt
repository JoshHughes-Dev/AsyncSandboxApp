package com.jhughes.sandboxapp.features.pureRetrofit

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jhughes.sandboxapp.R
import com.jhughes.sandboxapp.features.pureRetrofit.examples.*
import kotlinx.android.synthetic.main.activity_pure_examples.*

class PureExamplesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pure_examples)
        setSupportActionBar(toolbar_1)
        title = "Pure Examples"

        btn_simpleRetrofit.setOnClickListener { startActivity(RetrofitSimpleActivity::class.java) }
        btn_async_simple.setOnClickListener { startActivity(AsyncTaskSimpleActivity::class.java) }
        btn_async_weak_ref.setOnClickListener { startActivity(AsyncTaskWeakRefActivity::class.java) }
        btn_manual_handler.setOnClickListener { startActivity(ManualHandlerSimpleActivity::class.java) }
        btn_data_handler_simple.setOnClickListener { startActivity(DataHandlerSimpleActivity::class.java) }
        btn_data_handler_reg.setOnClickListener { startActivity(DataHandlerRegisteringActivity::class.java) }
        btn_data_handler_scoped.setOnClickListener { startActivity(DataHandlerScopedActivity::class.java) }
        btn_data_handler_callbacking.setOnClickListener { startActivity(CallbackOnlyActivity::class.java) }
        btn_data_handler_callback_requestState.setOnClickListener { startActivity(CallbackWithRequestStateActivity::class.java) }
        btn_data_handler_callback_delegated.setOnClickListener { startActivity(CallbackWithDelegatedUIActivity::class.java) }
    }

    private fun AppCompatActivity.startActivity(c: Class<*>) {
        startActivity(Intent(this, c))
    }
}