package com.telemedconnect.patient.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.telemedconnect.patient.lists.CountriesList
import com.telemedconnect.patient.databinding.LayoutCountryItemBinding
import com.telemedconnect.patient.data.models.Country
import com.telemedconnect.patient.ui.activities.ChooseCountryActivity

class CountriesAdapter (val context: Context, private val listener: OnItemClickListener) : RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Country)
    }

    private val itemList = CountriesList.countries
    private var filteredList: List<Country> = itemList.toList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesAdapter.ViewHolder {
        val binding = LayoutCountryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountriesAdapter.ViewHolder, position: Int) {
        holder.bind(filteredList[position])
    }

    override fun getItemCount(): Int = filteredList.size

    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            itemList.toList()
        } else {
            itemList.filter { it.name.contains(query, ignoreCase = true) } // Adjust filtering logic as needed
        }

        if(filteredList.isEmpty()){
            (context as ChooseCountryActivity).viewModel.countryListVisibility.set(View.GONE)
            context.viewModel.noResultsVisibility.set(View.VISIBLE)
        }else{
            (context as ChooseCountryActivity).viewModel.noResultsVisibility.set(View.GONE)
            context.viewModel.countryListVisibility.set(View.VISIBLE)
        }

        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: LayoutCountryItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(country: Country) {
            binding.country = country
            binding.executePendingBindings()
        }

        override fun onClick(view: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(itemList[position])
            }
        }
    }
}