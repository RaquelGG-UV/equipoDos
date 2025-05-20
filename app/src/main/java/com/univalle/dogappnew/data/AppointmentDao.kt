package com.univalle.dogappnew.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.univalle.dogappnew.model.Appointment
import kotlinx.coroutines.flow.Flow

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM appointments")
    fun getAllAppointments(): Flow<List<Appointment>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAppointment(appointment: Appointment)
}