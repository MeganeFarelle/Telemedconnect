package com.telemedconnect.patient.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.telemedconnect.patient.data.entities.Subscription
import com.telemedconnect.patient.databinding.LayoutSubscriptionItemBinding

class SubscriptionsAdapter(val context: Context, private val items: MutableList<Subscription>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnClickListener{
       fun onClick(item: Subscription, position: Int)
    }

    private lateinit var onClickListener: OnClickListener

    constructor(context: Context) : this(context, emptyList<Subscription>().toMutableList())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = LayoutSubscriptionItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return SubscriptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        (holder as SubscriptionViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(newData: List<Subscription>){
        items.clear()
        items.addAll(newData)
        notifyDataSetChanged()
    }

    fun setOnClickListener(listener: OnClickListener){
        onClickListener = listener
    }

    inner class SubscriptionViewHolder(private val binding: LayoutSubscriptionItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Subscription) {
            binding.item = item
            binding.executePendingBindings()

            binding.header.setOnClickListener{
                toggleSelect(item)
                if (onClickListener != null){
                    onClickListener.onClick(item, layoutPosition)
                }
            }

            if (item.plan == "Basic"){
                binding.desc.visibility = View.GONE
            }else{
                binding.desc.visibility = View.VISIBLE
            }
        }

        private fun toggleSelect(subscription: Subscription) {
            for (item in items) {
                if (item != subscription) {
                    item.active = false
                    item.isExpanded = false
                }
            }

            subscription.active = true
            subscription.isExpanded = !subscription.isExpanded
            notifyDataSetChanged()
        }
    }
}