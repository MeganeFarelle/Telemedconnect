package com.telemedconnect.patient.api.models


import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json

@Serializable
data class RefreshSessionResponse(
    @SerialName("message") var message: String?=null,
    @SerialName("access_token") val access_token: String?=null
) {
    companion object {
        fun fromJson(json: String): RefreshSessionResponse {
            return Json.decodeFromString(json)
        }
    }
}