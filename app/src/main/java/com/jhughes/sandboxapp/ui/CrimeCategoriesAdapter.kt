package com.jhughes.sandboxapp.ui

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jhughes.domain.models.CrimeCategory
import com.jhughes.sandboxapp.R

class CrimeCategoriesAdapter() : RecyclerView.Adapter<CrimeCategoriesAdapter.CrimeCategoryRowViewHolder>() {

    val crimeCategories = mutableListOf<CrimeCategory>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = CrimeCategoryRowViewHolder(parent.inflate(R.layout.row_crime_category))

    override fun getItemCount() = crimeCategories.size

    override fun onBindViewHolder(holder: CrimeCategoryRowViewHolder, position: Int)
            = holder.bind(crimeCategories[position])

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    inner class CrimeCategoryRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(crimeCategory: CrimeCategory) {
            itemView.findViewById<TextView>(R.id.row_url)?.text = crimeCategory.url
            itemView.findViewById<TextView>(R.id.row_name)?.text = crimeCategory.name
        }
    }
}