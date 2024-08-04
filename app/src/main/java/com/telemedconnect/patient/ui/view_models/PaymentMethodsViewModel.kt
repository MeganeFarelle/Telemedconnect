package com.telemedconnect.patient.ui.view_models

import android.app.Application
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.telemedconnect.patient.api.Api
import com.telemedconnect.patient.data.entities.PaymentMethod
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PaymentMethodsViewModel(application: Application) : AndroidViewModel(application) {

    val noSaveMethodVisibility = ObservableField<Int>(View.GONE)
    val saveMethodsVisibility = ObservableField<Int>(View.GONE)
    val processingBarVisibility = ObservableField<Int>(View.VISIBLE)

    val activeMethod = ObservableField<PaymentMethod>()

    private val _paymentMethods = MutableLiveData<List<PaymentMethod>>()
    val paymentMethods: LiveData<List<PaymentMethod>> get() = _paymentMethods

    init {
        loadPaymentMethod()
    }

    private fun loadPaymentMethod(){
        val context = getApplication<Application>().applicationContext
        viewModelScope.launch {
            delay(2000)
            _paymentMethods.value = Api.getPaymentMethods(context)
        }
    }
}