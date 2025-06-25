package com.univalle.dogappnew.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.univalle.dogappnew.model.Appointment
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response

// Interfaces para las APIs
interface DogApiService {
    @GET("breed/{breed}/images/random")
    suspend fun getRandomImageByBreed(@Path("breed") breed: String): DogImageResponse
}

interface DogBreedsApiService {
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): Response<DogBreedsResponse>
}

// Data classes para las respuestas
data class DogImageResponse(val message: String, val status: String)
data class DogBreedsResponse(val message: Map<String, List<String>>, val status: String)

class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()
    private val appointmentsCollection = "appointments"

    // Configurar Retrofit para ambas APIs
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val dogApi = retrofit.create(DogApiService::class.java)
    private val breedsApi = retrofit.create(DogBreedsApiService::class.java)

    // Obtener todas las citas
    suspend fun getAllAppointments(): List<Appointment> {
        return try {
            val result = db.collection(appointmentsCollection).get().await()
            result.documents.mapNotNull { doc ->
                doc.toObject(Appointment::class.java)?.apply {
                    id = doc.id.hashCode()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    // Insertar nueva cita
    suspend fun insertAppointment(appointment: Appointment) {
        try {
            db.collection(appointmentsCollection).add(appointment).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Actualizar cita existente
    suspend fun updateAppointment(appointment: Appointment) {
        try {
            val querySnapshot = db.collection(appointmentsCollection)
                .whereEqualTo("nombreMascota", appointment.nombreMascota)
                .whereEqualTo("nombrePropietario", appointment.nombrePropietario)
                .get().await()

            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents[0]
                db.collection(appointmentsCollection)
                    .document(document.id)
                    .set(appointment).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Eliminar cita
    suspend fun deleteAppointment(appointment: Appointment) {
        try {
            val querySnapshot = db.collection(appointmentsCollection)
                .whereEqualTo("nombreMascota", appointment.nombreMascota)
                .whereEqualTo("nombrePropietario", appointment.nombrePropietario)
                .get().await()

            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents[0]
                db.collection(appointmentsCollection)
                    .document(document.id)
                    .delete().await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Obtener cita por ID
    suspend fun getAppointmentById(id: Int): Appointment? {
        return try {
            val appointments = getAllAppointments()
            appointments.find { it.id == id }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Obtener razas desde la API
    suspend fun getRazas(): List<String> {
        return try {
            val response = breedsApi.getAllBreeds()
            if (response.isSuccessful) {
                response.body()?.message?.keys?.toList() ?: getDefaultBreeds()
            } else {
                getDefaultBreeds()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            getDefaultBreeds()
        }
    }

    // Obtener imagen por raza
    suspend fun getImageByBreed(breed: String): String {
        return try {
            val response = dogApi.getRandomImageByBreed(breed.lowercase())
            response.message
        } catch (e: Exception) {
            e.printStackTrace()
            "https://images.dog.ceo/breeds/hound-afghan/n02088094_1007.jpg"
        }
    }

    // Lista de razas por defecto si falla la API
    private fun getDefaultBreeds(): List<String> {
        return listOf(
            "akita", "beagle", "boxer", "bulldog", "chihuahua",
            "dachshund", "dalmatian", "germanshepherd", "golden",
            "husky", "labrador", "poodle", "rottweiler", "terrier",
            "affenpinscher", "afghan", "basset", "bloodhound", "borzoi"
        )
    }
}