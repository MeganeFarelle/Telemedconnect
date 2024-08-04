package com.telemedconnect.patient.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Specialization(
    @SerialName("id") val id: String?=null,
    @SerialName("officer_id") val officer_id: String?=null,
    @SerialName("certificate") val certificate: String?=null,
    @SerialName("time_obtained") val time_obtained: String?=null,
    @SerialName("years_practicing") val years_practicing: Int?=null,
    @SerialName("specialization") val specialization: String?=null,
    @SerialName("description") val description: String?=null,
)
