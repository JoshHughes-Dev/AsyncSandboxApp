package com.jhughes.sandboxapp.features.liveData.examples

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jhughes.sandboxapp.R
import com.jhughes.sandboxapp.ui.CrimeCategoriesAdapter
import kotlinx.android.synthetic.main.activity_feature.*


class SimpleArchActivity : AppCompatActivity() {

    private lateinit var viewModel: SimpleArchViewModel

    private val adapter = CrimeCategoriesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature)
        recycler.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(SimpleArchViewModel::class.java)

        viewModel.crimeCategories.observe(this, Observer { categories ->
            adapter.crimeCategories.clear()
            if(categories != null) {
                recycler.visibility = View.VISIBLE
                adapter.crimeCategories.addAll(categories)
            } else {
                recycler.visibility = View.GONE
            }
        })

        viewModel.error.observe(this, Observer { error ->
            if (error != null) {
                txt_error.visibility = View.VISIBLE
            } else {
                txt_error.visibility = View.GONE
            }
        })

        viewModel.loadingState.observe(this, Observer {
            if(it == true) {
                loading.show()
            } else {
                loading.hide()
            }
        })

        btn_fetch_categories.setOnClickListener { viewModel.fetchCrimeCategories() }
    }

}