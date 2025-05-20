package com.univalle.dogappnew.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.univalle.dogappnew.data.AppointmentDB
import com.univalle.dogappnew.model.Appointment
import com.univalle.dogappnew.repository.AppointmentRepository
import kotlinx.coroutines.launch

class AppointmentViewModel(application: Application) : AndroidViewModel(application) {
    val repository: AppointmentRepository

    init {
        val dao = AppointmentDB.getDatabase(application).appointmentDao()
        repository = AppointmentRepository(dao)
    }

    fun insertAppointment(appointment: Appointment) {
        repository.insert(appointment)
    }
}