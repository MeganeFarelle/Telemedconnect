package com.telemedconnect.patient.ui.view_models

import android.app.Application
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.telemedconnect.patient.data.entities.Account
import kotlin.math.ceil

class ProfessionalInfoViewModel(application : Application) : AndroidViewModel(application) {
    val progress = ObservableField<Int>(5)
    val continueEnabled = ObservableField<Boolean>(false)

    val licence = ObservableField<String>("")
    val yearsOfPractice = ObservableField<String>("")
    val affiliation = ObservableField<String>("")

    var user = Account()

    fun obtainProgress() {
        val s1 = if (licence.get()!!.isEmpty()) 0 else 1
        val s2 = if (yearsOfPractice.get()!!.isEmpty()) 0 else 1
        val s3 = if (affiliation.get()!!.isEmpty()) 0 else 1

        val p = ceil((
                (s1 + s2 + s3).toDouble()*100/3)
        ).toInt()

        progress.set(p)
        continueEnabled.set(p == 100)
    }

    init {

        licence.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                obtainProgress()
            }
        })

        yearsOfPractice.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                obtainProgress()
            }
        })

        affiliation.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                obtainProgress()
            }
        })
    }

}