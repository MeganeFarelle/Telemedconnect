package com.telemedconnect.patient.api.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
open class BaseResponse(
    @SerialName("message") var message: String?=null,
)
{
    companion object {
        fun fromJson(json: String): BaseResponse {
            return Json.decodeFromString(json)
        }
    }
}
