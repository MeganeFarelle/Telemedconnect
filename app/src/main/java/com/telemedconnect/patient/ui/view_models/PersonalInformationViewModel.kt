package com.telemedconnect.patient.ui.view_models

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.google.common.collect.HashBiMap
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.telemedconnect.patient.data.entities.Account
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import java.time.format.DateTimeFormatter
import kotlin.math.ceil

class PersonalInformationViewModel(application : Application) : AndroidViewModel(application) {
    val title = ObservableField<String>()
    val prompt = ObservableField<String>()
    val progress = ObservableField<Int>(5)
    val continueEnabled = ObservableField<Boolean>(false)

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

    val dateFormatApp = DateTimeFormatter.ofPattern("MMMM dd yyyy")
    val dateFormatStored = DateTimeFormatter.ofPattern("yyyy-MM-DD")

    private val appContext: Context = getApplication<Application>().applicationContext

    private var biMap : HashBiMap<Int, String> = HashBiMap.create<Int, String>()
    private var monthMap :  HashBiMap<String, Int> = HashBiMap.create<String, Int>()

    var user = Account()

    private fun setUser(){
        firstName.set(user.first_name?: "")
        lastName.set(user.last_name?: "")
        gender.set(biMap[user.gender]?: "Gender")

        try{
            val phoneNumberUtil = PhoneNumberUtil.getInstance()
            val phoneNumber = phoneNumberUtil.parse(user.primary_phone?: "", null)
            dialCode.set("+${phoneNumber.countryCode}")
            phone.set(phoneNumber?.nationalNumber.toString())
        }catch (_: Exception){}

        if(user.dob != null){
            dobMonth.set(monthMap.inverse()[user.dob!!.monthNumber])
            dobDay.set(user.dob!!.dayOfMonth.toString())
            dobYear.set(user.dob!!.year.toString())
        }

    }

    fun getInputDate(){
        try {
            val dob = "$dobMonth $dobDay, $dobYear"
            user.dob = LocalDate(
                year = Integer.parseInt(dobYear.get()!!),
                month = Month(Integer.parseInt(dobMonth.get()!!)),
                dayOfMonth = Integer.parseInt(dobDay.get()!!)
                )
        }catch (_: Exception){ }

        LocalDate
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

        val p = ceil((
                (s1*4 + (s2 + s3 + s4 + s5 + s6 + s7 + s8 + s9)*7)*100/60).toDouble()
        ).toInt()

        progress.set(p)
        continueEnabled.set(p >= 10)
    }

    init {
        biMap[0] = "Male"
        biMap[1] = "Female"
        biMap[2] = "Others"

        monthMap["January"] = 1
        monthMap["February"] = 2
        monthMap["March"] = 3
        monthMap["April"] = 4
        monthMap["May"] = 5
        monthMap["June"] = 6
        monthMap["July"] = 7
        monthMap["August"] = 8
        monthMap["September"] = 9
        monthMap["October"] = 10
        monthMap["November"] = 11
        monthMap["December"] = 12

        profilePicture.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                obtainProgress()
            }
        })

        firstName.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                user.first_name = firstName.get()
                obtainProgress()
            }
        })

        lastName.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                user.last_name = lastName.get()
                obtainProgress()
            }
        })

        dobDay.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                getInputDate()
                obtainProgress()
            }
        })

        dobMonth.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                getInputDate()
                obtainProgress()
            }
        })

        dobYear.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                getInputDate()
                obtainProgress()
            }
        })

        dialCode.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                user.primary_phone = "${dialCode.get()} ${phone.get()}"
                obtainProgress()
            }
        })

        phone.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                user.primary_phone = "${dialCode.get()} ${phone.get()}"
                obtainProgress()
            }
        })

        gender.addOnPropertyChangedCallback(object : OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                user.gender = biMap.inverse()[gender.get()]
                obtainProgress()
            }
        })

    }

}