package com.telemedconnect.patient.data.models

import com.telemedconnect.patient.data.entities.Transaction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Authority(
    @SerialName("authority") val authority: String? = null,
){
    companion object {
        fun fromJson(json: String): Transaction {
            return Json.decodeFromString(json)
        }
    }
}