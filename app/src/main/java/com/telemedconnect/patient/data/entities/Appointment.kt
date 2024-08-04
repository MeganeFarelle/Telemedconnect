package com.telemedconnect.patient.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Appointment(
    @SerialName("coordinates") var coordinates: String? = null,
    @SerialName("date_created") val date_created: String? =null,
    @SerialName("doctor") val doctor: Doctor?=null,
    @SerialName("end_time") var end_time: String?=null,
    @SerialName("id") val id: String?=null,
    @SerialName("invitee_id") var invitee_id: String?=null,
    @SerialName("location") var location: String? = null,
    @SerialName("mode") var mode: String?=null,
    @SerialName("organiser_id") var organiser_id: String? = null,
    @SerialName("purpose") var purpose: String? = null,
    @SerialName("start_time") var start_time: String?=null,
    @SerialName("status") val status: String?=null,
    @SerialName("invitee") var invitee: Doctor?=null
) {
    companion object {

        fun fromJson(json: String): Appointment {
            return Json{ignoreUnknownKeys=true}.decodeFromString(json)
        }

        val DATE_COMPARATOR = Comparator<Appointment> { e1, e2 ->
            e1.start_time!!.compareTo(e2.end_time!!)
        }
    }

}