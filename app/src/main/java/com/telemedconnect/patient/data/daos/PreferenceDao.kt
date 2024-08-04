package com.telemedconnect.patient.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.telemedconnect.patient.data.entities.Preference

@Dao
interface PreferenceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(preference: Preference)

    @Query("SELECT * FROM preferences WHERE key = :key")
    suspend fun getPreference(key: String): Preference?
}