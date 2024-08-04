package com.telemedconnect.patient.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.telemedconnect.patient.data.entities.Appointment
import com.telemedconnect.patient.databinding.LayoutCalenderItemBinding
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toLocalDateTime

class CalenderItemsAdapter : RecyclerView.Adapter<CalenderItemsAdapter.ItemsViewHolder>() {

    private var appointments = listOf<Appointment>()
    private var filteredAppointments = mutableListOf<Appointment>()

    var itemClickListener : OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val binding = LayoutCalenderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        holder.bind(appointments[position])
    }

    override fun getItemCount(): Int = appointments.size

    fun updateAppointments(update: List<Appointment>) {
        this.appointments = update
        this.filteredAppointments.addAll(update)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(list : OnItemClickListener){
        itemClickListener = list
    }

    fun filterData(date: LocalDate){
        filteredAppointments.clear()
        for(item in appointments){
            if(item.start_time?.toLocalDateTime()?.date  == date){
                filteredAppointments.add(item)
            }
        }

        notifyDataSetChanged()
    }

    inner class ItemsViewHolder(private val binding: LayoutCalenderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(event: Appointment) {
            binding.item = event
            binding.header.setOnClickListener{
                itemClickListener?.apply {
                    onItemClicked(item = event)
                }
            }
            binding.message.setOnClickListener{
                itemClickListener?.apply {
                    println("message clicked start")
                    onMessage(item = event)
                }
            }
            binding.call.setOnClickListener{
                itemClickListener?.apply {
                    println("call clicked start")
                    onCall(item = event)
                }
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClicked(item: Appointment)
        fun onMessage(item: Appointment)
        fun onCall(item: Appointment)
    }
}