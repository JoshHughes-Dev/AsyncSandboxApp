package com.jhughes.sandboxapp.features.rxjava

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jhughes.sandboxapp.R
import com.jhughes.sandboxapp.features.rxjava.examples.*
import kotlinx.android.synthetic.main.activity_rxjava_examples.*

class RxJavaExamplesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava_examples)
        setSupportActionBar(toolbar)
        title = "RxJava Examples"

        btn_rxjava.setOnClickListener { startActivity(RxJavaSimpleActivity::class.java) }
        btn_rxjava_dispose.setOnClickListener { startActivity(RxJavaSimpleDisposedActivity::class.java) }
        btn_rxjava_data_handler.setOnClickListener { startActivity(RxJavaDataHandlerActivity::class.java) }
        btn_rxjava_request_state.setOnClickListener { startActivity(RxJavaRequestStateActivity::class.java) }
        btn_rxjava_request_state_advanced.setOnClickListener { startActivity(RxJavaRequestStateAdvancedActivity::class.java) }
    }

    private fun AppCompatActivity.startActivity(c: Class<*>) {
        startActivity(Intent(this, c))
    }

}