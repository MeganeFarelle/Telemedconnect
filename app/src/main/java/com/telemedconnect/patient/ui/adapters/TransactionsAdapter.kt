package com.telemedconnect.patient.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.telemedconnect.patient.data.entities.Transaction
import com.telemedconnect.patient.databinding.LayoutTransactionItemBinding
import com.telemedconnect.patient.ui.activities.TransactionsActivity

class TransactionsAdapter(val context: Context, private val items: MutableList<Transaction>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    constructor(context: Context) : this(context, emptyList<Transaction>().toMutableList())

    private var filteredData = items.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = LayoutTransactionItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = filteredData[position]
        (holder as TransactionViewHolder).bind(item)
    }

    override fun getItemCount(): Int {
        return filteredData.size
    }

    fun setData(newData: List<Transaction>){
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
                if(str.lowercase() in item.toString().lowercase()){
                    filteredData.add(item)
                }
            }
        }

        filteredData = if (str.isEmpty()) {
            items.toMutableList()
        } else {
            items.filter { it.toString().contains(str, ignoreCase = true) }.toMutableList() // Adjust filtering logic as needed
        }

        if(filteredData.isEmpty()){
            (context as TransactionsActivity).viewModel.transactionsVisibility.set(View.GONE)
            context.viewModel.noResultsVisibility.set(View.VISIBLE)
        }else{
            (context as TransactionsActivity).viewModel.noResultsVisibility.set(View.GONE)
            context.viewModel.transactionsVisibility.set(View.VISIBLE)
        }

        notifyDataSetChanged()
    }

    inner class TransactionViewHolder(private val binding: LayoutTransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Transaction) {
            binding.item = item
            binding.executePendingBindings()

            binding.header.setOnClickListener{
                toggleExpanded(item)
            }
        }

        private fun toggleExpanded(transaction: Transaction) {
            for (item in filteredData) {
                if (item != transaction && item.isExpanded) {
                    item.isExpanded = false
                }
            }

            transaction.isExpanded = !transaction.isExpanded
            notifyDataSetChanged()
        }
    }
}