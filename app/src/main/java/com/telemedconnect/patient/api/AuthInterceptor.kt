package com.telemedconnect.patient.api

import okhttp3.Interceptor
import okhttp3.Request

class AuthInterceptor(private val token: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val newRequest: Request = chain.request().newBuilder()
            .apply {
                if (token != null) {
                    addHeader("Authorization", "Bearer $token")
                }
            }
            .build()

        return chain.proceed(newRequest)
    }
}