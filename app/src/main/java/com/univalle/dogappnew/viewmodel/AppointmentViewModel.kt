package com.univalle.dogappnew.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.univalle.dogappnew.model.Appointment
import com.univalle.dogappnew.model.UserRequest
import com.univalle.dogappnew.model.UserResponse
import com.univalle.dogappnew.repository.AppointmentRepository
import kotlinx.coroutines.launch

class AppointmentViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AppointmentRepository()
    val context = getApplication<Application>()

    private val _listRazas = MutableLiveData<List<String>>()
    private val _listPictures = MutableLiveData<String>()
    private val _listAppointments = MutableLiveData<MutableList<Appointment>>()
    private val _progresState = MutableLiveData(false)
    private val _currentAppointment = MutableLiveData<Appointment?>()

    val listRazas: LiveData<List<String>> = _listRazas
    val listPictures: LiveData<String> = _listPictures
    val listAppointments: LiveData<MutableList<Appointment>> = _listAppointments
    val progresState: LiveData<Boolean> = _progresState
    val currentAppointment: LiveData<Appointment?> = _currentAppointment

    private val _isRegister = MutableLiveData<UserResponse>()
    val isRegister: LiveData<UserResponse> = _isRegister

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

    fun insertAppointment(appointment: Appointment) {
        viewModelScope.launch {
            try {
                repository.insertAppointment(appointment)
                getAllAppointments()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

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

    fun getAppointmentById(id: Int) {
        viewModelScope.launch {
            try {
                _currentAppointment.value = repository.getAppointmentById(id)
            } catch (e: Exception) {
                e.printStackTrace()
                _currentAppointment.value = null
            }
        }
    }

    fun deleteAppointment(id: Int) {
        viewModelScope.launch {
            try {
                val appointments = repository.getAllAppointments()
                val appointmentToDelete = appointments.find { it.id == id }
                appointmentToDelete?.let {
                    repository.deleteAppointment(it)
                    getAllAppointments()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateAppointment(appointment: Appointment) {
        viewModelScope.launch {
            try {
                repository.updateAppointment(appointment)
                getAllAppointments()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

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

    fun registerUser(userRequest: UserRequest) {
        viewModelScope.launch {
            repository.registerUser(userRequest){ userResponse ->
                _isRegister.value = userResponse
            }
        }
    }

    fun loginUser(email: String, password: String, isLogin: (Boolean) -> Unit) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        isLogin(true)
                    } else {
                        isLogin(false)
                    }
                }
        } else {
            isLogin(false)
        }
    }

    fun sesion(email: String?, isEnableView: (Boolean) -> Unit) {
        if (email != null) {
            isEnableView(true)
        } else {
            isEnableView(false)
        }
    }
}