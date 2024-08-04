package com.telemedconnect.patient.data.entities

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Account (
    @SerialName("user_id") val user_id: String?=null,
    @SerialName("email") val email: String?=null,
    @SerialName("primary_phone") var primary_phone: String?=null,
    @SerialName("secondary_phone") val secondar_phonr: String?=null,
    @SerialName("status") val status: Int?=null,
    @SerialName("role") val role: Int?=null,
    @SerialName("availability_status") val availability_status: String?=null,
    @SerialName("first_name") var first_name: String?=null,
    @SerialName("last_name") var last_name: String?=null,
    @SerialName("dob") var dob: LocalDate?=null,
    @SerialName("gender") var gender: Int?=null,
    @SerialName("address") val address: String?=null,
    @SerialName("pp_url") val pp_url: String?=null,
) {
    companion object {
        fun fromJson(json: String): Account {
            return Json.decodeFromString(json)
        }
    }

    override fun toString(): String {
        return Json.encodeToString(this)
    }

    fun notComplete() : Boolean {
        return primary_phone.isNullOrEmpty()
                || first_name.isNullOrEmpty()
                || last_name.isNullOrEmpty()
    }

}