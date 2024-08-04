package com.telemedconnect.patient.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.telemedconnect.patient.databinding.LayoutHomeAppointmentBinding
import com.telemedconnect.patient.data.entities.Appointment

class AppointmentsAdapter(val context: Context, private val items: MutableList<Appointment>, val type: Int = 0) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var filteredData = items.toMutableList()
    private val MIN_ITEMS_TO_SHOW = 1
    var isExpanded: Boolean = true

    constructor(context: Context, type: Int = 0): this(context, emptyList<Appointment>().toMutableList(), type)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (type) {
            0 -> {
                val binding = LayoutHomeAppointmentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                HomeAppointmentViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (type) {
            0 -> {
                val item = filteredData[position]
                (holder as HomeAppointmentViewHolder).bind(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredData.size
    }

    fun setData(newData: List<Appointment>){
        items.clear()
        items.addAll(newData)

        isExpanded = true
        updateItems()
        isExpanded = !isExpanded
    }

    fun filterData(str: String){
        filteredData.clear()
        if(str.isEmpty()){
            filteredData = items.toMutableList()
        }else{
//            for(item in items){
//                if(str.lowercase() in context.resources.getText(item.textMain).toString().lowercase()
//                    || str.lowercase() in context.resources.getText(item.textSupport).toString().lowercase()){
//                    filteredData.add(item)
//                }
//            }
//            Log.v("filter_string", "$str, $filteredData")
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

    class HomeAppointmentViewHolder(private val binding: LayoutHomeAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Appointment) {
            binding.item = item
            binding.executePendingBindings()
        }
    }
}