package com.telemedconnect.patient.ui.activities

import android.os.Bundle
import com.telemedconnect.patient.databinding.ActivitySpecializationBinding

class SpecializationActivity: BaseActivity() {

    private lateinit var binding: ActivitySpecializationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpecializationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}