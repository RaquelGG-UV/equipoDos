package com.univalle.dogappnew.repository

import com.univalle.dogappnew.data.AppointmentDao
import com.univalle.dogappnew.model.Appointment
import kotlinx.coroutines.flow.Flow

class AppointmentRepository(private val dao: AppointmentDao) {
    fun insert(appointment: Appointment) {
        Thread {
            dao.insertAppointment(appointment)
        }.start()
    }

    val allAppointments = dao.getAllAppointments()
}
