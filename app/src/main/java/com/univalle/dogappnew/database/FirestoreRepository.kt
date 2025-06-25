package com.univalle.dogappnew.database

import com.google.firebase.firestore.FirebaseFirestore
import com.univalle.dogappnew.model.Appointment
import kotlinx.coroutines.tasks.await

class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("appointments")

    suspend fun getAllAppointments(): List<Appointment> {
        return try {
            val snapshot = collection.get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Appointment::class.java)?.apply {
                    id = doc.id.hashCode()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun insertAppointment(appointment: Appointment) {
        try {
            collection.add(appointment).await()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateAppointment(appointment: Appointment) {
        try {
            val snapshot = collection.get().await()
            val document = snapshot.documents.find { doc ->
                doc.id.hashCode() == appointment.id
            }
            document?.let {
                collection.document(it.id).set(appointment).await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun deleteAppointment(appointment: Appointment) {
        try {
            val snapshot = collection.get().await()
            val document = snapshot.documents.find { doc ->
                doc.id.hashCode() == appointment.id
            }
            document?.let {
                collection.document(it.id).delete().await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getAppointmentById(id: Int): Appointment? {
        return try {
            val snapshot = collection.get().await()
            snapshot.documents.find { doc ->
                doc.id.hashCode() == id
            }?.toObject(Appointment::class.java)?.apply {
                this.id = id
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}