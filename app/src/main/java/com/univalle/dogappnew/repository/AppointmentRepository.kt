package com.univalle.dogappnew.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.univalle.dogappnew.database.FirestoreRepository
import com.univalle.dogappnew.webservice.ApiUtils
import com.univalle.dogappnew.model.Appointment
import com.univalle.dogappnew.model.UserRequest
import com.univalle.dogappnew.model.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppointmentRepository {
    private val firestoreRepository = FirestoreRepository()
    private val apiService = ApiUtils.getApiService()
    private val apiService2 = ApiUtils.getApiService2()
    private val firebaseAuth = FirebaseAuth.getInstance()

    // CRUD Operations - Firebase
    suspend fun getAllAppointments(): List<Appointment> {
        return firestoreRepository.getAllAppointments()
    }

    suspend fun insertAppointment(appointment: Appointment) {
        firestoreRepository.insertAppointment(appointment)
    }

    suspend fun updateAppointment(appointment: Appointment) {
        firestoreRepository.updateAppointment(appointment)
    }

    suspend fun deleteAppointment(appointment: Appointment) {
        firestoreRepository.deleteAppointment(appointment)
    }

    suspend fun getAppointmentById(id: Int): Appointment? {
        return firestoreRepository.getAppointmentById(id)
    }

    // API Operations
    suspend fun getRazas(): List<String> {
        return try {
            val response = apiService.getAllBreeds()
            if (response.isSuccessful) {
                response.body()?.message?.keys?.toList() ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getImageByBreed(breed: String): String {
        return try {
            val response = apiService2.getImageByBreed(breed.lowercase())
            if (response.isSuccessful) {
                response.body()?.message ?: "https://images.dog.ceo/breeds/hound-afghan/n02088094_1007.jpg"
            } else {
                "https://images.dog.ceo/breeds/hound-afghan/n02088094_1007.jpg"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_1007.jpg"
        }
    }

    suspend fun registerUser(userRequest: UserRequest, userResponse: (UserResponse) -> Unit) {
        withContext(Dispatchers.IO){
            try {
                firebaseAuth.createUserWithEmailAndPassword(userRequest.email, userRequest.password)
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            val email = task.result?.user?.email
                            userResponse(
                                UserResponse(
                                    email = email,
                                    isRegister = true,
                                    message = "Registro Exitoso"
                                )
                            )
                        } else {
                            val error = task.exception
                            if (error is FirebaseAuthUserCollisionException) {
                                // Manejo específico cuando ya existe un mismo email registrado
                                userResponse(
                                    UserResponse(
                                        isRegister = false,
                                        message = "El usuario ya existe"
                                    )
                                )
                            } else {
                                // Manejo de otros errores
                                userResponse(
                                    UserResponse(
                                        isRegister = false,
                                        message = "Error en el registro"
                                    )
                                )
                            }
                        }
                    }
            } catch (e: Exception) {
                // Manejo de excepciones generales
                userResponse(
                    UserResponse(
                        isRegister = false,
                        message = e.message ?: "Error desconocido"
                    )
                )
            }
        }

    }
}