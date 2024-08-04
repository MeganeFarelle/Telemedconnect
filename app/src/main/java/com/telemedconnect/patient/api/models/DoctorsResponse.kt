package com.telemedconnect.patient.api.models


import com.telemedconnect.patient.data.entities.Doctor
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json

@Serializable
data class DoctorsResponse(
    @SerialName("message") var message: String?=null,
    @SerialName("appointments") val doctors: List<Doctor>?=null
){
    companion object {
        fun fromJson(json: String): AppointmentsResponse {
            return Json.decodeFromString(json)
        }
    }
}