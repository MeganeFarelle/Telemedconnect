package com.telemedconnect.patient.ui.activities

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.telemedconnect.patient.R
import com.telemedconnect.patient.data.models.Country
import com.telemedconnect.patient.databinding.ActivityPersonalInformationBinding
import com.telemedconnect.patient.ui.adapters.SpinnerAdapter
import com.telemedconnect.patient.ui.utils.MinMaxInputFilter
import com.telemedconnect.patient.ui.view_models.PersonalInformationViewModel
import com.telemedconnect.patient.utlis.Constants.EDIT_PROFILE
import com.telemedconnect.patient.utlis.Constants.NEW_PROFILE
import com.telemedconnect.patient.utlis.Constants.OPERATION


class PersonalInformationActivity : BaseActivity(){

    private lateinit var binding : ActivityPersonalInformationBinding
    private lateinit var viewModel: PersonalInformationViewModel

    private var operation = NEW_PROFILE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        operation = intent.getStringExtra(OPERATION)!!

        viewModel = ViewModelProvider(this).get(PersonalInformationViewModel::class.java)
        binding.viewModel = viewModel
        binding.activity = this

        binding.viewModel?.dialCode?.set("+230")
        binding.viewModel?.flag?.set(ResourcesCompat.getDrawable(resources, R.drawable.flag_mu, theme))

        val months = resources.getStringArray(R.array.months_array)
        val monthsAdapter = SpinnerAdapter(this, months.toList())
        monthsAdapter.setDropDownViewResource(R.layout.layout_month_spinner)

        binding.editProfilePicture.setOnClickListener {startCamera() }

        binding.monthsSpinner.adapter = monthsAdapter
        binding.monthsSpinner.setSelection(0, false)
        binding.monthsSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                viewModel.dobMonth.set(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle no selection
            }
        }

        binding.dobDay.filters = arrayOf(MinMaxInputFilter(1, 31))
        binding.dobYear.filters = arrayOf(MinMaxInputFilter(1, 2050))

        val genders = resources.getStringArray(R.array.genders)
        val genderAdapter = SpinnerAdapter(this, genders.toList())

        binding.genderSpinner.adapter = genderAdapter
        binding.genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                viewModel.gender.set(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle no selection
            }
        }
    }

    override fun onCountrySelected(c: Country) {
        binding.viewModel?.dialCode?.set(c.dialingCode)
        binding.viewModel?.flag?.set(ResourcesCompat.getDrawable(resources, c.flagResource, theme))
    }

    override fun onImageReady(imageBitmap: Bitmap) {
        super.onImageReady(imageBitmap)
        viewModel.profilePicture.set(imageBitmap)
    }

    override fun onAccountUpdated() {
        when(operation){
            EDIT_PROFILE -> {
                finish()
            }
            NEW_PROFILE -> {
                startActivity(Intent(this, InsuranceActivity::class.java))
                finish()
            }
        }
    }

}