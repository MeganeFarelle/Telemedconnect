package com.telemedconnect.patient.api.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class ChangeRoleRequest(
    @SerialName("new_role") var new_role: Int?=null,
)
