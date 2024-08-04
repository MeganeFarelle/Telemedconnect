package com.telemedconnect.patient.data.entities

import com.telemedconnect.patient.utlis.LocalDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.*
import kotlinx.serialization.json.Json

@Serializable
data class PaymentMethod(
    @SerialName("id") var id: String? = null,
    @SerialName("method_name") var methodName: String? = null,
    @SerialName("description") var description: String? = null,
    @SerialName("provider") var provider: String? = null,
    @SerialName("type") var type: String? = null,
    @SerialName("holder_name") var holderName: String = "Holder Name",
    @SerialName("account_number") var accountNumber: String = "XXXXXXXXXXXXXXXX",
    @SerialName("currency_supported") var currencySupported: String? = null,
    @SerialName("transaction_fee") var transactionFee: Double? = null,
    @SerialName("status") var status: Int? = null,
    @SerialName("date_created") @Serializable(with = LocalDateTimeSerializer::class) var dateCreated: LocalDateTime? = null,
    @SerialName("date_updated") @Serializable(with = LocalDateTimeSerializer::class) var dateUpdated: LocalDateTime? = null
){
    companion object {
        fun fromJson(json: String): PaymentMethod {
            return Json.decodeFromString(json)
        }

        val STATUS_COMPARATOR = Comparator<PaymentMethod> { e1, e2 ->
            e2.status!!.compareTo(e1.status!!)
        }

    }
}