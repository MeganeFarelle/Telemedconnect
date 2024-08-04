package com.telemedconnect.patient.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProvider
import com.telemedconnect.patient.databinding.ActivityInsuranceBinding
import com.telemedconnect.patient.ui.view_models.InsuranceViewModel


class InsuranceActivity : BaseActivity(){

    private lateinit var binding : ActivityInsuranceBinding
    private lateinit var viewModel: InsuranceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsuranceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(InsuranceViewModel::class.java)
        binding.viewModel = viewModel

        binding.insuranceNameInputLayout.isEndIconVisible = !binding.insuranceName.text.isNullOrEmpty()

        binding.insuranceName.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                binding.insuranceNameInputLayout.isEndIconVisible = !s.isNullOrEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not used
            }
        })

        binding.insuranceNameInputLayout.setEndIconOnClickListener {
            binding.insuranceName.text.clear()
        }

    }

    fun onSave(){
        startActivity(Intent(this, BridgeActivity::class.java))
        finish()
    }

    fun onSkip(){
        startActivity(Intent(this, BridgeActivity::class.java))
        finish()
    }

}

