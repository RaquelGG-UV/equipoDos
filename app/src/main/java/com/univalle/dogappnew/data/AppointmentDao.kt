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
    fun getAllAppointments(): MutableList<Appointment>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAppointment(appointment: Appointment)

    @Query("SELECT * FROM appointments WHERE id = :id")
    fun getAppointmentById(id: Int): Appointment?

    @Query("DELETE FROM appointments WHERE id = :id")
    fun deleteAppointment(id: Int)

    @Query("UPDATE appointments SET nombreMascota = :nombreMascota, raza = :raza, nombrePropietario = :nombrePropietario, telefono = :telefono WHERE id = :id")
    fun updateAppointment(id: Int, nombreMascota: String, raza: String, nombrePropietario: String, telefono: String)
}
    
