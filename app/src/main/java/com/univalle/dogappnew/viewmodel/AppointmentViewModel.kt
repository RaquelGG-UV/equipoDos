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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AppointmentViewModel(application: Application) : AndroidViewModel(application) {
    val repository: AppointmentRepository
    val context = getApplication<Application>()

    private val _listRazas = MutableLiveData<List<String>>()
    private val _listPictures = MutableLiveData<List<String>>()

    val listRazas:LiveData<List<String>> = _listRazas
    val listPictures:LiveData<List<String>> = _listPictures

    private val _listAppointments = MutableLiveData<MutableList<Appointment>>()
    val listAppointments: LiveData<MutableList<Appointment>> = _listAppointments

    private val _progresState = MutableLiveData(false)
    val progresState: LiveData<Boolean> = _progresState


    init {
        val dao = AppointmentDB.getDatabase(application).appointmentDao()
        repository = AppointmentRepository(dao)
    }

    fun getAllAppointments() {
        viewModelScope.launch {
            try{
                _listAppointments.value = repository.getAllAppointments()
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
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

//    fun getPicture(){
//        viewModelScope.launch {
//            try{
//                _listPictures.value = repository.getPicture()
//            }catch (e: Exception){
//                e.printStackTrace()
//            }
//        }
//    }
}