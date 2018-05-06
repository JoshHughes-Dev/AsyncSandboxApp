package com.jhughes.sandboxapp.features.liveData

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jhughes.sandboxapp.R
import com.jhughes.sandboxapp.features.liveData.examples.ArchPostingFromThreadActivity
import com.jhughes.sandboxapp.features.liveData.examples.SimpleArchActivity
import kotlinx.android.synthetic.main.activity_arch_examples.*

class AndroidArchitectureExamples : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arch_examples)
        setSupportActionBar(toolbar_arch)
        title = "Android arch Examples"

        btn_arch.setOnClickListener { startActivity(SimpleArchActivity::class.java) }
        btn_arch_posting.setOnClickListener { startActivity(ArchPostingFromThreadActivity::class.java) }
    }

    private fun AppCompatActivity.startActivity(c: Class<*>) {
        startActivity(Intent(this, c))
    }
}