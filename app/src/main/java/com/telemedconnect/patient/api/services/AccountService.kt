package com.telemedconnect.patient.api.services


import com.telemedconnect.patient.api.models.AccountResponse
import com.telemedconnect.patient.api.models.AppointmentResponse
import com.telemedconnect.patient.api.models.AppointmentsResponse
import com.telemedconnect.patient.api.models.BaseResponse
import com.telemedconnect.patient.api.models.ChangeRoleRequest
import com.telemedconnect.patient.api.models.DoctorsResponse
import com.telemedconnect.patient.api.models.ChangeRoleResponse
import com.telemedconnect.patient.api.models.GetProInfoResponse
import retrofit2.Call
import retrofit2.http.Headers
import com.telemedconnect.patient.data.entities.Account
import com.telemedconnect.patient.data.entities.Appointment
import com.telemedconnect.patient.data.entities.ProInfo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


interface AccountService{

    @Headers("Content-Type: application/json")
    @GET("account/operations/get-details")
    fun getAccount(): Call<AccountResponse>


    @Headers("Content-Type: application/json")
    @PUT("account/operations/update-details")
    fun updateAccount(@Body account: Account): Call<AccountResponse>

    @Headers("Content-Type: application/json")
    @GET("account/operations/get-appointments")
    fun getAppointments(): Call<AppointmentsResponse>

    @GET("account/operations/find-doctors")
    fun findDoctors(@Query("speciality") speciality: String): Call<DoctorsResponse>

    @POST("account/operations/book-appointment")
    fun bookAppointment(@Body requestBody: Appointment): Call<AppointmentResponse>

    @PUT("account/operations/change-role")
    fun changeRole(@Body requestBody: ChangeRoleRequest): Call<ChangeRoleResponse>

    @GET("med-pro/operations/get-professional-info")
    fun getProInfo(): Call<GetProInfoResponse>

    @POST("med-pro/operations/add-professional-info")
    fun addProInfo(@Body requestBody: ProInfo): Call<BaseResponse>

}

