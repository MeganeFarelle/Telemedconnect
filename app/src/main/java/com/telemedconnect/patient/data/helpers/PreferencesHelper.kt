package com.telemedconnect.patient.data.helpers

import com.telemedconnect.patient.data.daos.PreferenceDao
import com.telemedconnect.patient.data.entities.Account
import com.telemedconnect.patient.data.repositories.PreferenceRepository


class PreferencesHelper(preferenceDao: PreferenceDao) {

    private val repository = PreferenceRepository(preferenceDao)

    suspend fun saveString(key: String, value: String) {
        repository.savePreference(key, value)
    }

    suspend fun saveInt(key: String, value: Int) {
        repository.savePreference(key, value)
    }

    suspend fun saveBoolean(key: String, value: Boolean) {
        repository.savePreference(key, value)
    }

    suspend fun saveAccount(key: String, value: Account) {
        repository.savePreference(key, value)
    }

    suspend fun getString(key: String): String? {
        return repository.getPreference(key) as? String
    }

    suspend fun getInt(key: String): Int? {
        return repository.getPreference(key) as? Int
    }

    suspend fun getBoolean(key: String): Boolean? {
        return repository.getPreference(key) as? Boolean
    }

    suspend fun getAccount(key: String): Account? {
        return repository.getPreference(key) as? Account
    }
}