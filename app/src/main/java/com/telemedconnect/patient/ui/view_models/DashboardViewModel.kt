package com.telemedconnect.patient.ui.view_models

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.telemedconnect.patient.api.RetrofitClient
import com.telemedconnect.patient.api.models.AppointmentsResponse
import com.telemedconnect.patient.api.models.DoctorsResponse
import com.telemedconnect.patient.data.entities.Account
import com.telemedconnect.patient.data.entities.Appointment
import com.telemedconnect.patient.data.entities.Doctor
import com.telemedconnect.patient.data.helpers.PreferencesHelper
import com.telemedconnect.patient.data.local.AppDatabase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardViewModel(application: Application) : AndroidViewModel(application) {

    var accessToken: String? = null
    var user : Account? = null
    var name = ObservableField<String>()
    val noAppointments = ObservableField<Int>(View.GONE)
    val appointmentVisibility = ObservableField<Int>(View.GONE)
    val processingBarVisibility = ObservableField<Int>(View.VISIBLE)
    val messagingReady = ObservableField<Boolean>(false)
    val messagingError = ObservableField<Boolean>(false)

    private val _appointments = MutableLiveData<List<Appointment>>()
    val appointments: LiveData<List<Appointment>> get() = _appointments

    private val _doctors = MutableLiveData<List<Doctor>>()
    val doctors: LiveData<List<Doctor>> get() = _doctors

//    private fun loadAppointments(){
//        val context = getApplication<Application>().applicationContext
//        viewModelScope.launch {
//            delay(2000)
//            _appointments.value = Api.getUpcomingAppointments(context)
//        }
//    }

    fun loadAppointments(){

        val call = RetrofitClient.createAccountService(accessToken).getAppointments()

        call.enqueue(object : Callback<AppointmentsResponse> {
            override fun onResponse(call: Call<AppointmentsResponse>, response: Response<AppointmentsResponse>) {

                var appointmentsResponse = response.body()

                if (response.isSuccessful) {
                    //onAccountUpdated(accountResponse?.account_details!!)
                   _appointments.value = appointmentsResponse?.appointments?: mutableListOf()
                }else{
                    appointmentsResponse = AppointmentsResponse.fromJson(response.errorBody()!!.string())
                }

                println("appointments: ${appointmentsResponse!!.message}")
                println("appointments: ${appointmentsResponse.appointments}")

                Toast.makeText(
                    getApplication(),
                    "${appointmentsResponse.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onFailure(call: Call<AppointmentsResponse>, t: Throwable) {
//                Toast.makeText(getApplication(), "Token Refresh Failed", Toast.LENGTH_SHORT).show()
//                Timber.tag("Token Refresh Error").v(t.message.toString())
                println("appointments: Loading failed")
            }

        })
    }

    fun fetchDoctorsBySpeciality(speciality: String?="") {

        val call = RetrofitClient.createAccountService(accessToken).findDoctors(speciality!!)

        call.enqueue(object : Callback<DoctorsResponse> {
            override fun onResponse(call: Call<DoctorsResponse>, response: Response<DoctorsResponse>
            ) {
                if (response.isSuccessful) {
                    val doctors = response.body()!!.doctors
                    println("Doctors: $doctors")
                    _doctors.value = response.body()!!.doctors?: mutableListOf()
                } else {
                    println("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<DoctorsResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}