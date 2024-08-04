package com.telemedconnect.patient.api

import android.content.Context
import android.util.Log
import com.telemedconnect.patient.data.entities.Appointment
import com.telemedconnect.patient.data.entities.Transaction
import com.telemedconnect.patient.data.entities.PaymentMethod
import com.telemedconnect.patient.data.entities.Subscription
import org.json.JSONArray

class Api {

    companion object {

        fun getAppointments(context: Context): MutableList<Appointment> {
            val result: MutableList<Appointment> = mutableListOf()
            val jString = context.assets.open("sample_appointments.json").bufferedReader()
                .use { it.readText() }

            val jsonArray = JSONArray(jString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val appointment = Appointment.fromJson(jsonObject.toString())
                result.add(appointment)
                //println("1 -> ID: ${appointment.id}, Date: ${appointment.start_time.date}, Reason: ${appointment.purpose}")
            }

            return result.sortedWith(Appointment.DATE_COMPARATOR).toMutableList()
        }

        fun getUpcomingAppointments(context: Context): MutableList<Appointment>{
            val result: MutableList<Appointment> = mutableListOf()
            val allAppointments = getAppointments(context)
            for(appointment in allAppointments){
                if(appointment.status == "Scheduled"){
                    result.add(appointment)
                    //println("2 -> ID: ${appointment.id}, Date: ${appointment.startTime.date}, Reason: ${appointment.purpose}")
                }
            }

            return result
        }

        fun getTransactions(context: Context): MutableList<Transaction> {
            val result: MutableList<Transaction> = mutableListOf()
            val jString = context.assets.open("sample_transactions.json").bufferedReader()
                .use { it.readText() }

            val jsonArray = JSONArray(jString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                Log.v("transaction", jsonObject.toString())
                val transaction = Transaction.fromJson(jsonObject.toString())
                result.add(transaction)
            }

            return result.sortedWith(Transaction.DATE_COMPARATOR).toMutableList()
        }

        fun getPaymentMethods(context: Context): MutableList<PaymentMethod>{
            val result: MutableList<PaymentMethod> = mutableListOf()
            val jString = context.assets.open("sample_payment_methods.json").bufferedReader()
                .use { it.readText() }

            val jsonArray = JSONArray(jString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val paymentMethod = PaymentMethod.fromJson(jsonObject.toString())
                result.add(paymentMethod)
            }

            return result.sortedWith(PaymentMethod.STATUS_COMPARATOR).toMutableList()
        }

        fun getSubscriptions(context: Context): MutableList<Subscription>{
            val result: MutableList<Subscription> = mutableListOf()
            val jString = context.assets.open("sample_subscriptions.json").bufferedReader()
                .use { it.readText() }

            val jsonArray = JSONArray(jString)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val subscription = Subscription.fromJson(jsonObject.toString())
                result.add(subscription)
            }

            return result
        }
    }
}