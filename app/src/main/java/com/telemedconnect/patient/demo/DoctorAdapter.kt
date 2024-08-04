package com.telemedconnect.patient.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.telemedconnect.patient.R

class DoctorAdapter(private val doctorList: List<Doctor>) :
    RecyclerView.Adapter<DoctorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.demo_layout_doctor_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val doctor = doctorList[position]
        holder.doctorNameTextView.text = doctor.name
        holder.doctorSpecializationTextView.text = doctor.specialization
    }

    override fun getItemCount(): Int {
        return doctorList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val doctorNameTextView: TextView = view.findViewById(R.id.doctorNameTextView)
        val doctorSpecializationTextView: TextView = view.findViewById(R.id.doctorSpecializationTextView)
    }
}