package com.telemedconnect.patient.data.entities

data class InsuranceProvider(
    val id: String? = null,          // Unique identifier for the insurance company
    val name: String,        // Name of the insurance company
    val contactEmail: String? = null, // Contact email for the insurance company
    val contactPhone: String? = null, // Contact phone number for the insurance company
    val country: String? = null,      // Country where the insurance company is located
    val website: String? = null // Website URL of the insurance company (optional)
)

