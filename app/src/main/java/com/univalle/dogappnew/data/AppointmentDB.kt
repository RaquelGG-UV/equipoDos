package com.univalle.dogappnew.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.univalle.dogappnew.model.Appointment

@Database(entities = [Appointment::class], version = 3, exportSchema = false)
abstract class AppointmentDB : RoomDatabase() {

    abstract fun appointmentDao(): AppointmentDao

    companion object {
        @Volatile
        private var INSTANCE: AppointmentDB? = null

        fun getDatabase(context: Context): AppointmentDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppointmentDB::class.java,
                    "appointment_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}