package com.telemedconnect.patient.data.entities

import android.view.View
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Subscription(
    @SerialName("id") val id: String? = null,
    @SerialName("plan") val plan: String? = null,
    @SerialName("price") val price: String? = null,
    @SerialName("period") val period: String? = null,
    @SerialName("savings") val savings: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("details") val details: String? = null,
    @SerialName("active") var active: Boolean = false
){
    var isExpanded = false

    fun getDetailsVisibility(): Int{
        return if (isExpanded) View.VISIBLE else View.GONE
    }

    companion object {
        fun fromJson(json: String): Subscription {
            return Json.decodeFromString(json)
        }
    }
}

