package com.telemedconnect.patient.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.telemedconnect.patient.data.models.Country
import com.telemedconnect.patient.databinding.ActivityChooseCountryBinding
import com.telemedconnect.patient.ui.adapters.CountriesAdapter
import com.telemedconnect.patient.ui.view_models.ChooseCountryViewModel

class ChooseCountryActivity : BaseActivity() {

    private lateinit var binding: ActivityChooseCountryBinding
    lateinit var viewModel: ChooseCountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ChooseCountryViewModel::class.java)
        binding.activity = this
        binding.viewModel = viewModel

        // Setup dialog UI components and actions
        val adapter = CountriesAdapter(this, object : CountriesAdapter.OnItemClickListener{
            override fun onItemClick(item: Country) {
                val returnIntent = Intent().apply {
                    putExtra("country", item)
                }
                setResult(Activity.RESULT_OK, returnIntent)
                finish()
            }
        })

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.searchLayout.isEndIconVisible = !binding.search.text.isNullOrEmpty()
        binding.searchLayout.setEndIconOnClickListener {
            binding.search.text.clear()
        }

        binding.search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                binding.searchLayout.isEndIconVisible = !p0.isNullOrEmpty()
                adapter.filter(p0.toString())
            }
        })
    }

    fun backPressed(){
        if(viewModel.isSearchVisible){
            viewModel.toggleSearch()
        }else{
            finish()
        }
    }
}