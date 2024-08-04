package com.telemedconnect.patient.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json

@Serializable
data class ResendVcResponse(
    @SerialName("vc_token") var vc_token: String?=null,
    @SerialName("message") val message: String?=null
) {
    companion object {
        fun fromJson(json: String): ResendVcResponse {
            return Json.decodeFromString(json)
        }
    }
}