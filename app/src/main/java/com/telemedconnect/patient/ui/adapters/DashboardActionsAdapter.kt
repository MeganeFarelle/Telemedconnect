package com.telemedconnect.patient.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.telemedconnect.patient.databinding.LayoutDashboardHomeItemBinding
import com.telemedconnect.patient.databinding.LayoutDashboardSearchItemBinding
import com.telemedconnect.patient.data.models.DashboardAction

class DashboardActionsAdapter(val context: Context, private val items: MutableList<DashboardAction>, val type: Int = 0) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var filteredData = items.toMutableList()
    private val MIN_ITEMS_TO_SHOW = 4
    var isExpanded: Boolean = true


    private lateinit var listener : ItemClickListener

    interface ItemClickListener{
        fun onItemClicked()
    }

    fun setItemClickListener(listener: ItemClickListener){
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (type) {
            0 -> {
                val binding = LayoutDashboardHomeItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeViewHolder(binding)
            }
            1 -> {
                val binding = LayoutDashboardSearchItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                SearchViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (type) {
            0 -> {
                val item = filteredData[position]
                (holder as HomeViewHolder).bind(item)
            }
            1 -> {
                val item = filteredData[position]
                (holder as SearchViewHolder).bind(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredData.size
    }

    fun filterData(str: String){
        filteredData.clear()
        if(str.isEmpty()){
            filteredData = items.toMutableList()
        }else{
            for(item in items){
                if(str.lowercase() in context.resources.getText(item.textMain).toString().lowercase()
                    || str.lowercase() in context.resources.getText(item.textSupport).toString().lowercase()){
                    filteredData.add(item)
                }
            }
        }

        notifyDataSetChanged()
    }

    fun toggle() {
        updateItems()
        isExpanded = !isExpanded
    }

    private fun updateItems() {
        filteredData = if (isExpanded) items.take(MIN_ITEMS_TO_SHOW).toMutableList() else items
        notifyDataSetChanged()
    }

    inner class HomeViewHolder(private val binding: LayoutDashboardHomeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DashboardAction) {
            binding.item = item
            binding.executePendingBindings()

        }
    }

    inner class SearchViewHolder(private val binding: LayoutDashboardSearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DashboardAction) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}