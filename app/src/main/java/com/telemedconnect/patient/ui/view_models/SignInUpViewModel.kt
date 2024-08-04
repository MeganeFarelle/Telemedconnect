package com.telemedconnect.patient.ui.view_models

import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.telemedconnect.patient.api.models.AuthResponse

class SignInUpViewModel : ViewModel() {
    val email = ObservableField<String>()
    val password = ObservableField<String>()
    val confirmPassword = ObservableField<String>()
    val rem30Days = ObservableField<Boolean>()
    val proceedEnabled = ObservableField<Boolean>()
    val authResponse = ObservableField<AuthResponse>()

    init {
        email.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                proceedEnabled.set(!(email.get().isNullOrEmpty() or password.get().isNullOrEmpty()))
            }
        })

        password.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                proceedEnabled.set(!(email.get().isNullOrEmpty() or password.get().isNullOrEmpty()))
            }
        })
    }
}