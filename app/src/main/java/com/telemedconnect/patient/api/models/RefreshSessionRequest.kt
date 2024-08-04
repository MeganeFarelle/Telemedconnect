package com.telemedconnect.patient.api.models


import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class RefreshSessionRequest(
    @SerialName("exp_time") var exp_time: Int?=null,
)