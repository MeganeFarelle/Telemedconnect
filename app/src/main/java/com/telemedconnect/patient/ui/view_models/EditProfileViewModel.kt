package com.telemedconnect.patient.ui.view_models

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.telemedconnect.patient.data.entities.InsuranceProvider
import kotlin.math.ceil

class EditProfileViewModel : ViewModel() {

    val profilePicture = ObservableField<Bitmap>()
    val firstName = ObservableField<String>("")
    val lastName = ObservableField<String>("")
    val dobDay = ObservableField<String>("")
    val dobMonth = ObservableField<String>("Month")
    val dobYear = ObservableField<String>("")
    val dialCode = ObservableField<String>("")
    val phone = ObservableField<String>("")
    val gender = ObservableField<String>("Gender")
    val flag = ObservableField<Drawable>()
    var insuranceProvider = ObservableField<InsuranceProvider>()
    val insurancePolicyId = ObservableField<String>("")

    init {

        profilePicture.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                obtainProgress()
            }
        })

        firstName.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                obtainProgress()
            }
        })

        lastName.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                obtainProgress()
            }
        })

        dobDay.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                obtainProgress()
            }
        })

        dobMonth.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                obtainProgress()
            }
        })

        dobYear.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                obtainProgress()
            }
        })

        dialCode.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                obtainProgress()
            }
        })

        phone.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                obtainProgress()
            }
        })

        gender.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                obtainProgress()
            }
        })

    }

    fun obtainProgress() {
        val s1 = if (profilePicture.get() == null) 0 else 1
        val s2 = if (firstName.get()!!.isEmpty()) 0 else 1
        val s3 = if (lastName.get()!!.isEmpty()) 0 else 1
        val s4 = if (gender.get().equals("Gender")) 0 else 1
        val s5 = if (dobDay.get()!!.isEmpty()) 0 else 1
        val s6 = if (dobYear.get()!!.isEmpty()) 0 else 1
        val s7 = if (dialCode.get()!!.isEmpty()) 0 else 1
        val s8 = if (phone.get()!!.isEmpty()) 0 else 1
        val s9 = if (dobMonth.get().equals("Month")) 0 else 1
    }

}