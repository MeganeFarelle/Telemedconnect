package com.telemedconnect.patient.api.models


import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class ResendVcRequest(
    @SerialName("email") var email: String?=null,
    @SerialName("vc_token") val vc_token: String?=null
)
