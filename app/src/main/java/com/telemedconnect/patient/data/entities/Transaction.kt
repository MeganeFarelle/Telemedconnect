package com.telemedconnect.patient.data.entities

import android.view.View
import com.telemedconnect.patient.utlis.LocalDateTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json

@Serializable
data class Transaction(
    @SerialName("id") val id: String,
    @SerialName("transaction_type") val transactionType: String,
    @SerialName("amount") val amount: Double,
    @SerialName("currency") val currency: String,
    @SerialName("status") val status: String,
    @SerialName("host_id") val senderId: String,
    @SerialName("merchant_name") val merchantName: String,
    @SerialName("merchant_id") val merchantId: String,
    @SerialName("method_id") val paymentMethodId: String,
    @SerialName("account_number") val accountNumber: String,
    @SerialName("transaction_date") @Serializable(with = LocalDateTimeSerializer::class) val transactionDate: LocalDateTime,
    @SerialName("reference_number") val referenceNumber: String,
    @SerialName("description") val description: String,
    @SerialName("fee") val fee: Double,
    @SerialName("exchange_rate") val exchangeRate: Double,
    @SerialName("original_amount") val originalAmount: Double,
    @SerialName("original_currency") val originalCurrency: String,
    @SerialName("created_at") @Serializable(with = LocalDateTimeSerializer::class) val createdAt: LocalDateTime,
    @SerialName("updated_at") @Serializable(with = LocalDateTimeSerializer::class) val updatedAt: LocalDateTime
){

    override fun toString(): String {
        return "Transaction(id='$id', " +
                "transactionType='$transactionType', " +
                "amount=$amount, " +
                "currency='$currency', " +
                "status='$status', " +
                "senderId='$senderId', " +
                "merchantName='$merchantName'," +
                " merchantId='$merchantId', " +
                "paymentMethodId='$paymentMethodId', " +
                "accountNumber='$accountNumber', " +
                "transactionDate='$transactionDate', " +
                "referenceNumber='$referenceNumber', " +
                "description='$description', " +
                "fee=$fee, " +
                "exchangeRate=$exchangeRate, " +
                "originalAmount=$originalAmount, " +
                "originalCurrency='$originalCurrency', " +
                "createdAt='$createdAt', " +
                "updatedAt='$updatedAt')"
    }

    var isExpanded = false

    fun getDetailsVisibility(): Int{
        return if (isExpanded) View.VISIBLE else View.GONE
    }

    companion object {
        fun fromJson(json: String): Transaction {
            return Json.decodeFromString(json)
        }

        val DATE_COMPARATOR = Comparator<Transaction> { e1, e2 ->
            e1.createdAt.compareTo(e2.createdAt)
        }
    }
}
