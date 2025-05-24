package com.univalle.dogappnew.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.univalle.dogappnew.data.AppointmentDB
import com.univalle.dogappnew.model.Appointment
import com.univalle.dogappnew.model.Razas
import com.univalle.dogappnew.repository.AppointmentRepository
import kotlinx.coroutines.launch

class AppointmentViewModel(application: Application) : AndroidViewModel(application) {
    val repository: AppointmentRepository
    val context = getApplication<Application>()
    //private val appointmentRepository = AppointmentRepository(context)

    private val _listRazas = MutableLiveData<MutableList<Razas>>()

    val listRazas:LiveData<MutableList<Razas>> = _listRazas

    init {
        val dao = AppointmentDB.getDatabase(application).appointmentDao()
        repository = AppointmentRepository(dao)
    }

    fun insertAppointment(appointment: Appointment) {
        repository.insert(appointment)
    }

    fun getRazas(){
        viewModelScope.launch {
            try{
                _listRazas.value = repository.getRazas()

            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}