package com.univalle.dogappnew.repository

import com.univalle.dogappnew.database.FirestoreRepository
import com.univalle.dogappnew.webservice.ApiUtils
import com.univalle.dogappnew.model.Appointment

class AppointmentRepository {
    private val firestoreRepository = FirestoreRepository()
    private val apiService = ApiUtils.getApiService()
    private val apiService2 = ApiUtils.getApiService2()

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
}