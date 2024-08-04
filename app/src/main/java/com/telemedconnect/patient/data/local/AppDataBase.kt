package com.telemedconnect.patient.data.local


import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.telemedconnect.patient.data.daos.PreferenceDao
import com.telemedconnect.patient.data.entities.Preference


@Database(entities = [Preference::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun preferenceDao(): PreferenceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}