package com.telemedconnect.patient.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.telemedconnect.patient.R
import com.telemedconnect.patient.databinding.LayoutMonthSpinnerBinding.*

class SpinnerAdapter(
    context: Context,
    private val items: List<String>
) : ArrayAdapter<String>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = inflate(LayoutInflater.from(context), parent, false)
        binding.textView.text = getItem(position)
        if (position == 0){
            binding.textView.setTextColor(context.getColor(R.color.grey))
        }else{
            binding.textView.setTextColor(context.getColor(R.color.black))
        }
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = inflate(LayoutInflater.from(context), parent, false)
        binding.textView.text = getItem(position)
        if (position == 0){
            binding.textView.setTextColor(context.getColor(R.color.grey))
        }else{
            binding.textView.setTextColor(context.getColor(R.color.black))
        }
        return binding.root
    }
}