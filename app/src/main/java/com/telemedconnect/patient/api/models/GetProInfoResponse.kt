package com.telemedconnect.patient.api.models

import com.telemedconnect.patient.data.entities.ProInfo
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json

@Serializable
data class GetProInfoResponse(
    @SerialName("message") var message: String?=null,
    @SerialName("info") val info: ProInfo?=null
){
    companion object {
        fun fromJson(json: String): GetProInfoResponse {
            return Json.decodeFromString(json)
        }
    }
}