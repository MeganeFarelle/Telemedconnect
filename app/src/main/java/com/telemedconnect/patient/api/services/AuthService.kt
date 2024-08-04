package com.telemedconnect.patient.api.services


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import com.telemedconnect.patient.api.models.AuthResponse
import com.telemedconnect.patient.api.models.AuthRequest
import com.telemedconnect.patient.api.models.RefreshSessionRequest
import com.telemedconnect.patient.api.models.RefreshSessionResponse
import com.telemedconnect.patient.api.models.ResendVcRequest
import com.telemedconnect.patient.api.models.ResendVcResponse
import com.telemedconnect.patient.api.models.ValidateVcRequest
import com.telemedconnect.patient.api.models.ValidateVcResponse


interface AuthService {
    @Headers("Content-Type: application/json")
    @POST("auth/account/sign-up")
    fun signupAccount(@Body request: AuthRequest): Call<AuthResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/account/sign-in")
    fun signInAccount(@Body request: AuthRequest): Call<AuthResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/validation-code/verify")
    fun verifyVc(@Body request: ValidateVcRequest): Call<ValidateVcResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/validation-code/resend")
    fun resendVc(@Body request: ResendVcRequest): Call<ResendVcResponse>

    @Headers("Content-Type: application/json")
    @POST("auth/session/refresh")
    fun refreshSession(@Body request: RefreshSessionRequest): Call<RefreshSessionResponse>
}
