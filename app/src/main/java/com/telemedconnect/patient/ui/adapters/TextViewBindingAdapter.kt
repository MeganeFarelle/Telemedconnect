package com.telemedconnect.patient.ui.adapters

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.telemedconnect.patient.R
import com.telemedconnect.patient.data.entities.Appointment
import com.telemedconnect.patient.data.entities.Doctor
import com.telemedconnect.patient.data.entities.Subscription
import com.telemedconnect.patient.data.entities.Transaction

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Currency
import java.util.Locale


object TextViewBindingAdapter {

    val rfc1123Formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH)

    private fun capWord(input: String): String {
        val data = input.lowercase()
        return data.split(" ").joinToString(" ") { word ->
            word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }
    }

    private fun currencySymbol(currency: String) : String{

        val poundSymbol = Currency.getInstance("GBP").symbol
        val usdSymbol = Currency.getInstance("USD").symbol
        val euroSymbol = Currency.getInstance("EUR").symbol

        return when(currency){
            "GBP" -> {
                poundSymbol
            }

            "USD" -> {
                usdSymbol
            }

            "EUR" -> {
                euroSymbol
            }

            else ->{
                ""
            }
        }
    }

    @JvmStatic
    @BindingAdapter("dialCode")
    fun setDialCode(textView : TextView, code: String?){
        code?.let{ c ->
            "($c)".also { textView.text = it }
        }
    }

    @JvmStatic
    @BindingAdapter("date")
    fun setDate(textView : TextView, date: String?){
        date?.let{

            val zonedDateTime = ZonedDateTime.parse(it, rfc1123Formatter)

            // Format the date to the desired output
            val dayOfWeek = zonedDateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
            val dayOfMonth = zonedDateTime.dayOfMonth
            val month = zonedDateTime.month.getDisplayName(TextStyle.FULL, Locale.ENGLISH)

            "$dayOfWeek, $dayOfMonth $month".also { textView.text = it }
        }
    }

    @JvmStatic
    @BindingAdapter("time")
    fun setTime(textView : TextView, appointment: Appointment){
        appointment.let { apt ->
            val sZonedDateTime = ZonedDateTime.parse(apt.start_time, rfc1123Formatter)
            val start = sZonedDateTime.toLocalTime().toString()

            val eZonedDateTime = ZonedDateTime.parse(apt.end_time, rfc1123Formatter)
            val end = eZonedDateTime.toLocalTime().toString()

            "$start - $end".also { textView.text = it }
        }
    }

    @JvmStatic
    @BindingAdapter("time_v")
    fun setTimeV(textView : TextView, appointment: Appointment){
        appointment.let { apt ->
            val sZonedDateTime = ZonedDateTime.parse(apt.start_time, rfc1123Formatter)
            val start = sZonedDateTime.toLocalTime().toString()

            val eZonedDateTime = ZonedDateTime.parse(apt.end_time, rfc1123Formatter)
            val end = eZonedDateTime.toLocalTime().toString()

            "$start\n$end".also { textView.text = it }
        }
    }

    @JvmStatic
    @BindingAdapter("resendCountDown")
    fun setResendCountDown(textView : TextView, count: Int?){
        count?.let {
            "Resend in $count".also { textView.text = it }
        }
    }

    @JvmStatic
    @BindingAdapter("doctorName")
    fun setDoctorName(textView : TextView, doctor: Doctor?){
        doctor?.let {
            "Dr. ${it.first_name} ${it.last_name}".also { textView.text = it }
        }
    }

    @JvmStatic
    @BindingAdapter("doctorSpecialization")
    fun setDoctorSpecialization(textView : TextView, doctor: Doctor?){

        doctor?.specialization.let {specs->
            if(specs!!.isNotEmpty()){
                specs.get(0).also { textView.text = it.specialization }
            }else{
                "General Medicine".also {textView.text = it }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("dayOfMonth")
    fun setDay(textView : TextView, transaction: Transaction?){
        val day = transaction?.transactionDate?.dayOfMonth.toString()
        transaction?.let {
            textView.text = day
        }
    }

    @JvmStatic
    @BindingAdapter("month")
    fun setMonth(textView : TextView, transaction: Transaction?){
        val month = transaction?.transactionDate?.month?.name?.take(3)
        transaction?.let {
            textView.text = capWord(month!!)
        }
    }

    @JvmStatic
    @BindingAdapter("transactionAmount")
    fun setTransactionAmount(textView : TextView, transaction: Transaction?){

        transaction?.let {
            val sign = if (transaction.transactionType == "Credit") "+" else "-"
            val color = if(transaction.transactionType == "Credit") R.color.black else R.color.red

            val currency = currencySymbol(transaction.currency)
            "$sign${currency} ${transaction.amount}".also { textView.text = it }
            textView.setTextColor(ContextCompat.getColor(textView.context, color))
        }
    }

    @JvmStatic
    @BindingAdapter("originalAmount")
    fun setOriginalAmount(textView : TextView, transaction: Transaction?){
        transaction?.let {
            val currency = currencySymbol(transaction.originalCurrency)
            "$currency ${transaction.originalAmount}".also { textView.text = it }
        }
    }

    @JvmStatic
    @BindingAdapter("transactionFee")
    fun setTransactionFee(textView : TextView, transaction: Transaction?){
        transaction?.let {
            val currency = currencySymbol(transaction.currency)
            "$currency ${transaction.fee}".also { textView.text = it }
        }
    }

    @JvmStatic
    @BindingAdapter("exchangeRate")
    fun exchangeRate(textView : TextView, transaction: Transaction?){
        transaction?.let {
            "${transaction.exchangeRate}".also { textView.text = it }
        }
    }

    @JvmStatic
    @BindingAdapter("saving")
    fun setSavings(textView : TextView, subscription: Subscription?){
        subscription?.let {
            "SAVE ${subscription.savings}%".also { textView.text = it }
        }
    }

    @JvmStatic
    @BindingAdapter("savingsVisibility")
    fun setSavingsVisibility(textView : TextView, subscription: Subscription?){
        textView.visibility = if(subscription?.savings!!.isEmpty()) View.GONE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("subscriptionTextColor")
    fun setSubscriptionTextColor(textView : TextView, subscription: Subscription?){
        val color = if(subscription?.active!!) R.color.white else R.color.black
        val context = textView.context
        textView.setTextColor(context.resources.getColor(color, context.theme))
    }

}