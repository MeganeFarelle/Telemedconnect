package com.telemedconnect.patient.ui.view_models

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.telemedconnect.patient.data.entities.InsuranceProvider

class InsuranceViewModel(application : Application) : AndroidViewModel(application) {
    var insuranceProvider = ObservableField<InsuranceProvider>()
    val insurancePolicyId = ObservableField<String>("")

}