package com.univalle.dogappnew.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.univalle.dogappnew.model.Appointment
import com.univalle.dogappnew.repository.FirestoreRepository
import kotlinx.coroutines.launch

class AppointmentViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FirestoreRepository()
    val context = getApplication<Application>()

    private val _listRazas = MutableLiveData<List<String>>()
    private val _listPictures = MutableLiveData<String>()

    val listRazas: LiveData<List<String>> = _listRazas
    val listPictures: LiveData<String> = _listPictures

    private val _listAppointments = MutableLiveData<MutableList<Appointment>>()
    val listAppointments: LiveData<MutableList<Appointment>> = _listAppointments

    private val _progresState = MutableLiveData(false)
    val progresState: LiveData<Boolean> = _progresState

    private val _currentAppointment = MutableLiveData<Appointment?>()
    val currentAppointment: LiveData<Appointment?> = _currentAppointment

    // Obtener todas las citas desde Firestore
    fun getAllAppointments() {
        viewModelScope.launch {
            try {
                _progresState.value = true
                val appointments = repository.getAllAppointments()
                _listAppointments.value = appointments.toMutableList()
            } catch (e: Exception) {
                e.printStackTrace()
                _listAppointments.value = mutableListOf()
            } finally {
                _progresState.value = false
            }
        }
    }

    // Insertar nueva cita en Firestore
    fun insertAppointment(appointment: Appointment) {
        viewModelScope.launch {
            try {
                repository.insertAppointment(appointment)
                getAllAppointments() // Refrescar la lista
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Obtener razas desde la API (mantener funcionalidad existente)
    fun getRazas() {
        viewModelScope.launch {
            try {
                _progresState.value = true
                _listRazas.value = repository.getRazas()
            } catch (e: Exception) {
                e.printStackTrace()
                _listRazas.value = emptyList()
            } finally {
                _progresState.value = false
            }
        }
    }

    // Obtener imagen por raza
    fun getPicture(raza: String) {
        viewModelScope.launch {
            try {
                _progresState.value = true
                _listPictures.value = repository.getImageByBreed(raza)
            } catch (e: Exception) {
                e.printStackTrace()
                _listPictures.value = ""
            } finally {
                _progresState.value = false
            }
        }
    }

    // Obtener cita por ID (simulado con búsqueda)
    fun getAppointmentById(id: Int) {
        viewModelScope.launch {
            try {
                val appointments = repository.getAllAppointments()
                _currentAppointment.value = appointments.find { it.id == id }
            } catch (e: Exception) {
                e.printStackTrace()
                _currentAppointment.value = null
            }
        }
    }

    // Eliminar cita
    fun deleteAppointment(id: Int) {
        viewModelScope.launch {
            try {
                val appointments = repository.getAllAppointments()
                val appointmentToDelete = appointments.find { it.id == id }
                appointmentToDelete?.let {
                    repository.deleteAppointment(it)
                    getAllAppointments() // Refrescar la lista
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Actualizar cita existente
    fun updateAppointment(appointment: Appointment) {
        viewModelScope.launch {
            try {
                repository.updateAppointment(appointment)
                getAllAppointments() // Refrescar la lista
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Obtener imagen por raza con callback
    fun getImageByBreed(breed: String, callback: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val imageUrl = repository.getImageByBreed(breed)
                callback(imageUrl)
            } catch (e: Exception) {
                e.printStackTrace()
                callback("https://images.dog.ceo/breeds/hound-afghan/n02088094_1007.jpg")
            }
        }
    }
}