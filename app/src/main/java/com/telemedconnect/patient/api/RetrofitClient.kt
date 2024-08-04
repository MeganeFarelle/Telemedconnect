package com.telemedconnect.patient.api


import com.telemedconnect.patient.api.services.AccountService
import com.telemedconnect.patient.api.services.AuthService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://18.212.38.21:5000/telemedconnect/api/"

    val json = Json { ignoreUnknownKeys = true }

    private fun getInstance(token : String?= null) : Retrofit {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    fun createAuthService(token: String?=null) : AuthService {
        return getInstance(token).create(AuthService::class.java)
    }

    fun createAccountService(token: String?=null) : AccountService{
        return getInstance(token).create(AccountService::class.java)
    }

}