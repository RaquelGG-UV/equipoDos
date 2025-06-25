package com.univalle.dogappnew.repository

import com.univalle.dogappnew.data.local.FirestoreRepository
import com.univalle.dogappnew.data.remote.ApiClient
import com.univalle.dogappnew.model.Appointment

class AppointmentRepository {
    private val firestoreRepository = FirestoreRepository()
    private val dogApiService = ApiClient.dogApiService
    private val breedsApiService = ApiClient.breedsApiService
    private val razasApiService = ApiClient.razasApiService

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

    suspend fun getRazas(): List<String> {
        return try {
            val response = razasApiService.getRazas()
            if (response.isSuccessful) {
                response.body()?.message ?: emptyList()
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
            val response = dogApiService.getRandomImageByBreed(breed.lowercase())
            response.message
        } catch (e: Exception) {
            e.printStackTrace()
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_1007.jpg"
        }
    }
}