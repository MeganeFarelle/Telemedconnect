package com.telemedconnect.patient.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json


@Serializable
data class ValidateVcRequest(
    @SerialName("vc_code") var vc_code: String?=null,
    @SerialName("vc_token") val vc_token: String?=null
){
    companion object {
        fun fromJson(json: String): ValidateVcRequest {
            return Json.decodeFromString(json)
        }
    }
}