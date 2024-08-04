package com.telemedconnect.patient.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class AuthRequest(
    @SerialName("email") var email: String?=null,
    @SerialName("password") val password: String?=null
)