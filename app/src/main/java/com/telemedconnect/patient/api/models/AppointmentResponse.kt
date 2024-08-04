package com.telemedconnect.patient.api.models

import com.telemedconnect.patient.data.entities.Appointment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data class AppointmentResponse(
    @SerialName("appointment") val appointment: Appointment?=null,
    @SerialName("message") val message: String? = null
){
    companion object {
        fun fromJson(json: String): AppointmentResponse {
            return Json.decodeFromString(json)
        }
    }
}
