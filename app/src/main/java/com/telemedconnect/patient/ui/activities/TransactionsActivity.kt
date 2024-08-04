package com.telemedconnect.patient.ui.activities


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.telemedconnect.patient.databinding.ActivityTransactionsBinding
import com.telemedconnect.patient.ui.adapters.TransactionsAdapter
import com.telemedconnect.patient.ui.utils.SpaceItemDecoration
import com.telemedconnect.patient.ui.view_models.TransactionsViewModel

class TransactionsActivity : BaseActivity() {

    private lateinit var binding: ActivityTransactionsBinding
    lateinit var viewModel: TransactionsViewModel

    private lateinit var transactionsAdapter: TransactionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(TransactionsViewModel::class.java)

        binding.activity = this
        binding.viewModel = viewModel

        transactionsAdapter = TransactionsAdapter(this)
        binding.transactionsRecyclerView.apply {
            adapter = transactionsAdapter
            layoutManager = LinearLayoutManager(applicationContext)
            addItemDecoration(SpaceItemDecoration(30, 0))
        }

        viewModel.transactions.observe(this, Observer {data ->
            transactionsAdapter.setData(data)
            viewModel.processingBarVisibility.set(View.GONE)

            if(data.isEmpty()){
                viewModel.transactionsVisibility.set(View.GONE)
                viewModel.searchIconVisibility.set(View.GONE)
                viewModel.noTransactionsVisibility.set(View.VISIBLE)
            }else{
                viewModel.noTransactionsVisibility.set(View.GONE)
                viewModel.transactionsVisibility.set(View.VISIBLE)
                viewModel.searchIconVisibility.set(View.VISIBLE)
            }
        })

        binding.searchLayout.setStartIconOnClickListener { viewModel.toggleSearch() }

        binding.searchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.searchLayout.isEndIconVisible = !s.isNullOrEmpty()
                transactionsAdapter.filterData(s.toString())
            }
        })

        binding.searchLayout.setEndIconOnClickListener {
            binding.searchBox.text.clear()
        }
    }

    fun backPressed(){
        finish()
    }

}