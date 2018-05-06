package com.jhughes.sandboxapp.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.jhughes.domain.models.CrimeCategory
import com.jhughes.sandboxapp.R
import kotlinx.android.synthetic.main.activity_feature.*

abstract class BaseActivity: AppCompatActivity() {

    internal val adapter = CrimeCategoriesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature)

        btn_fetch_categories.setOnClickListener { fetchCrimeCategories() }
        recycler.adapter = adapter
    }

    abstract fun fetchCrimeCategories()

    fun handleSuccess(crimeCategories: List<CrimeCategory>) {
        Log.d("MainActivity", "count: " + crimeCategories.size)
        txt_error.visibility = View.GONE
        recycler.visibility = View.VISIBLE
        adapter.crimeCategories.clear()
        adapter.crimeCategories.addAll(crimeCategories)
    }

    fun handleError(throwable: Throwable) {
        Log.d("MainActivity", "error: " + throwable.message)
        txt_error.visibility = View.VISIBLE
        recycler.visibility = View.GONE
        adapter.crimeCategories.clear()
    }
}