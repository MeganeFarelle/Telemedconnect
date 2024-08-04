package com.telemedconnect.patient.api.models


import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json

@Serializable
data class AuthResponse(
    @SerialName("message") val message: String?= null,
    @SerialName("vc_token") val vc_token: String?=null,
    @SerialName("access_token") val access_token: String?=null
) {
    companion object {
        fun fromJson(json: String): AuthResponse {
            return Json.decodeFromString(json)
        }
    }
}

