package com.telemedconnect.patient.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.telemedconnect.patient.data.entities.Doctor
import com.telemedconnect.patient.databinding.LayoutDoctorItemBinding
import com.telemedconnect.patient.ui.activities.TransactionsActivity

class DoctorsAdapter(val context: Context, private val items: MutableList<Doctor>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        interface OnItemClickListener{
            fun onItemClick(item : Doctor)
        }

    lateinit var mListener : OnItemClickListener

    private var filteredData = items.toMutableList()

    constructor(context: Context, listener: OnItemClickListener) : this(context, emptyList<Doctor>().toMutableList()){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = LayoutDoctorItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return DoctorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = filteredData[position]
        (holder as DoctorViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return filteredData.size
    }

    fun setData(newData: List<Doctor>){
        items.clear()
        items.addAll(newData)

        filteredData.clear()
        filteredData.addAll(newData)

        notifyDataSetChanged()
    }

    fun resetItems(){
        filteredData.clear()
        filteredData.addAll(items)
        notifyDataSetChanged()
    }

    fun filterData(str: String){

        filteredData.clear()
        if(str.isEmpty()){
            filteredData = items.toMutableList()
        }else{
            for(item in items){
                val specs = item.specialization
                if (specs != null) {
                    for(spec in specs){
                        val name = spec.specialization
                        val desc = spec.description
                        if(name!!.contains(str) || desc!!.contains(str)){
                            filteredData.add(item)
                        }
                    }
                }
            }
        }

        notifyDataSetChanged()
    }

    inner class DoctorViewHolder(private val binding: LayoutDoctorItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Doctor) {
            binding.item = item
            binding.executePendingBindings()

            binding.header.setOnClickListener{
                toggleExpanded(item)
                mListener.onItemClick(item)
            }
        }

        private fun toggleExpanded(doctor: Doctor) {

            for (item in filteredData) {
                if (item != doctor && item.isExpanded) {
                    item.isExpanded = false
                }
            }

            doctor.isExpanded = !doctor.isExpanded
            notifyDataSetChanged()
        }
    }

}