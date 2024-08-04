package com.telemedconnect.patient.lists

import com.telemedconnect.patient.data.entities.PaymentMethod

object PaymentMethodsList {
    var payment_methods = listOf(
        PaymentMethod(id="1", methodName = "MasterCard"),
        PaymentMethod(id="2", methodName = "VisaCard"),
        PaymentMethod(id="3", methodName = "Paypal"),
        PaymentMethod(id="4", methodName = "ApplePay"),
    )
}