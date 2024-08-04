package com.telemedconnect.patient.data.entities


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProInfo (
    @SerialName("id") val id: String?=null,
    @SerialName("officer_id") val officer_id: String?=null,
    @SerialName("licence") var licence: String?=null,
    @SerialName("time_obtained") val time_obtained: String?=null,
    @SerialName("years_practicing") var years_practicing: Int?=null,
    @SerialName("affiliation") var affiliation: String?=null
)
