package com.telemedconnect.patient.api.models


import com.telemedconnect.patient.data.entities.Appointment
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json

@Serializable
data class AppointmentsResponse(
    @SerialName("message") var message: String?=null,
    @SerialName("appointments") val appointments: List<Appointment>?=null
){
    companion object {
        fun fromJson(json: String): AppointmentsResponse {
            return Json.decodeFromString(json)
        }
    }
}