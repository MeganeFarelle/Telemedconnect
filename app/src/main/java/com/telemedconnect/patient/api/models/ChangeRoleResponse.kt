package com.telemedconnect.patient.api.models


import com.telemedconnect.patient.data.entities.Account
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json

@Serializable
data class ChangeRoleResponse (
    @SerialName("message") var message: String?=null,
    @SerialName("account_details") var account_details: Account?=null
){
    companion object {
        fun fromJson(json: String): ChangeRoleResponse {
            return Json.decodeFromString(json)
        }
    }
}
