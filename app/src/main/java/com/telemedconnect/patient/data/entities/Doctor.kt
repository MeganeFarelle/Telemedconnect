package com.telemedconnect.patient.data.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Doctor (
    @SerialName("user_id") val user_id: String?=null,
    @SerialName("email") val email: String?=null,
    @SerialName("primary_phone") var primary_phone: String?=null,
    @SerialName("secondary_phone") val secondary_phone: String?=null,
    @SerialName("status") val status: Int?=null,
    @SerialName("role") val role: Int?=null,
    @SerialName("availability_status") val availability_status: String?=null,
    @SerialName("first_name") var first_name: String?=null,
    @SerialName("last_name") var last_name: String?=null,
    @SerialName("dob") var dob: String?=null,
    @SerialName("gender") var gender: String?=null,
    @SerialName("address") val address: String?=null,
    @SerialName("specialization") val specialization: List<Specialization>?=null,
    @SerialName("pp_url") val pp_url: String?=null,
    @SerialName("professional_info") val professional_info: ProInfo?=null
) {

    var isExpanded = false

    companion object {
        fun fromJson(json: String): Account {
            return Json{ignoreUnknownKeys=true}.decodeFromString(json)
        }
    }

    override fun toString(): String {
        return Json.encodeToString(this)
    }

    fun generateHtml(includeHeader: Boolean = false) : String {
        val professionalInfo = this.professional_info
        val specializations = this.specialization!!.joinToString(separator = "<br><br>") { specialization ->
            """
        <div style="margin-bottom: 10px;">
            <h1 style="color: #0040DD;">${specialization.specialization}</h3>
            <p><strong>Certificate:</strong> ${specialization.certificate}</p>
            <p><strong>Time Obtained:</strong> ${specialization.time_obtained}</p>
            <p><strong>Years Practicing:</strong> ${specialization.years_practicing}</p>
            <p><strong>Description:</strong> ${specialization.description}</p>
        </div>
        """.trimIndent()
        }

        return """
    <html>
    <body>
        <div class="section">
            <h1 style="color: #0040DD;">${this.first_name} ${this.last_name}</h1>
            <p><strong>Email:</strong> ${this.email}</p>
            <p><strong>Primary Phone:</strong> ${this.primary_phone}</p>
            <p><strong>Secondary Phone:</strong> ${this.secondary_phone}</p>
            <p><strong>Status:</strong> ${if (this.status == 1) "Active" else "Inactive"}</p>
            <p><strong>Availability Status:</strong> ${this.availability_status}</p>
            <p><strong>Date of Birth:</strong> ${this.dob}</p>
            <p><strong>Gender:</strong> ${this.gender}</p>
            <p><strong>Address:</strong> ${this.address}</p>
        </div>
        <div class="section">
            <h1>Professional Information</h2>
            <p><strong>License:</strong> ${professionalInfo!!.licence}</p>
            <p><strong>Time Obtained:</strong> ${professionalInfo.time_obtained}</p>
            <p><strong>Years Practicing:</strong> ${professionalInfo.years_practicing}</p>
            <p><strong>Affiliation:</strong> ${professionalInfo.affiliation}</p>
        </div>
        <div class="section">
            <h2>Specializations</h2>
            $specializations
        </div>
    </body>
    </html>
    """.trimIndent()
    }
}

//<img src="${user.pp_url}" alt="Profile Picture" style="max-width:100%; height:auto;">


