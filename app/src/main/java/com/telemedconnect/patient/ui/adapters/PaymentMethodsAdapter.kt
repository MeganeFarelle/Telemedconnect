package com.telemedconnect.patient.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.telemedconnect.patient.data.entities.PaymentMethod
import com.telemedconnect.patient.databinding.LayoutPaymentMethodItemBinding
import com.telemedconnect.patient.databinding.LayoutStoredPaymentMethodItemBinding
import com.telemedconnect.patient.ui.utils.TextTransformMethod

class PaymentMethodsAdapter(val context: Context, private val items: MutableList<PaymentMethod>, val type: Int=0) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var itemClickListener : OnItemClickListener? = null

    constructor(context: Context, type: Int= 0) : this(
        context, emptyList<PaymentMethod>().toMutableList(), type)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when(type){
            1 -> {
                val binding = LayoutStoredPaymentMethodItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return StoredPaymentMethodsViewHolder(binding)
            }else-> {
                val binding = LayoutPaymentMethodItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return PaymentMethodsViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when(type){
            1 -> {
                (holder as StoredPaymentMethodsViewHolder).bind(item)
            }else-> {
                (holder as PaymentMethodsViewHolder).bind(item)
            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(newData: List<PaymentMethod>){
        items.clear()
        items.addAll(newData)

        notifyDataSetChanged()
    }

    fun setOnItemClickedListener(listener: OnItemClickListener){
        itemClickListener = listener
    }

    inner class PaymentMethodsViewHolder(private val binding: LayoutPaymentMethodItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PaymentMethod) {
            binding.item = item
            binding.main.setOnClickListener { itemClickListener?.onItemClicked(item) }
            binding.executePendingBindings()
        }
    }

    inner class StoredPaymentMethodsViewHolder(private val binding: LayoutStoredPaymentMethodItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PaymentMethod) {
            binding.item = item
            binding.accountNumber.transformationMethod = TextTransformMethod(TextTransformMethod.ACCOUNT_NUMBER)
            binding.main.setOnClickListener { itemClickListener?.onItemClicked(item) }
            binding.executePendingBindings()
        }
    }

    interface OnItemClickListener{
        fun onItemClicked(item: PaymentMethod)
    }
}