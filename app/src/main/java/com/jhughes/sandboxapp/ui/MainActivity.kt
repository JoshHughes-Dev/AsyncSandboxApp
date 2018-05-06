package com.jhughes.sandboxapp.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jhughes.sandboxapp.R
import com.jhughes.sandboxapp.features.coolbeans.CoolBeansActivity
import com.jhughes.sandboxapp.features.liveData.AndroidArchitectureExamples
import com.jhughes.sandboxapp.features.pureRetrofit.PureExamplesActivity
import com.jhughes.sandboxapp.features.rxjava.RxJavaExamplesActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_cool_beans.setOnClickListener { startActivity(CoolBeansActivity::class.java) }
        btn_pure_examples.setOnClickListener { startActivity(PureExamplesActivity::class.java) }
        btn_rxjava_examples.setOnClickListener { startActivity(RxJavaExamplesActivity::class.java) }
        btn_arch_examples.setOnClickListener { startActivity(AndroidArchitectureExamples::class.java) }

    }

    private fun AppCompatActivity.startActivity(c: Class<*>) {
        startActivity(Intent(this, c))
    }
}
