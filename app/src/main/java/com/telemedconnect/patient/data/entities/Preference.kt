package com.telemedconnect.patient.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "preferences")
data class Preference(
    @PrimaryKey val key: String,
    val value: String,
    val type: String
)
