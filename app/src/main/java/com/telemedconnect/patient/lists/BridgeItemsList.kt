package com.telemedconnect.patient.lists

import com.telemedconnect.patient.R
import com.telemedconnect.patient.data.models.BridgeItem
import com.telemedconnect.patient.data.models.OnboardingItem

object BridgeItemsList {
    var bridgeItems = listOf(
        BridgeItem(
            1,
            R.drawable.illustration_patient,
            R.string.patient,
            R.string.patient_desc
        ),

        BridgeItem(
            2,
            R.drawable.illustration_doctor,
            R.string.doctor,
            R.string.doctor_desc
        ),

        BridgeItem(
            3,
            R.drawable.illustration_pharmacist,
            R.string.pharmacist,
            R.string.pharmacist_desc
        ),

        BridgeItem(
            4,
            R.drawable.illustration_lab_tech,
            R.string.lab_tech,
            R.string.lab_tec_desc
        ),
    )
}