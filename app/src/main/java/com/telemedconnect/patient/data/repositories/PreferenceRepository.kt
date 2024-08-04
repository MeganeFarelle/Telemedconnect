package com.telemedconnect.patient.data.repositories

import com.telemedconnect.patient.data.daos.PreferenceDao
import com.telemedconnect.patient.data.entities.Account
import com.telemedconnect.patient.data.entities.Preference

class PreferenceRepository(private val preferenceDao: PreferenceDao) {

    suspend fun savePreference(key: String, value: Any){
        val type = when (value) {
            is String -> "String"
            is Int -> "Int"
            is Boolean -> "Boolean"
            is Account -> "Account"
            else -> throw IllegalArgumentException("Unsupported type")
        }

        val preference = Preference(key, value.toString(), type)
        preferenceDao.insert(preference)
    }

    suspend fun getPreference(key: String): Any? {
        val preference = preferenceDao.getPreference(key) ?: return null
        return when (preference.type) {
            "String" -> preference.value
            "Int" -> preference.value.toInt()
            "Boolean" -> preference.value.toBoolean()
            "Account" -> Account.fromJson(preference.value)
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }
}