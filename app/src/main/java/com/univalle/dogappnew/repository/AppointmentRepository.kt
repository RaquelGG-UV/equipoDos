package com.univalle.dogappnew.repository

import android.content.Context
import com.univalle.dogappnew.data.AppointmentDB
import com.univalle.dogappnew.data.AppointmentDao
import com.univalle.dogappnew.model.Appointment
import com.univalle.dogappnew.model.Razas
import com.univalle.dogappnew.webservice.ApiService
import com.univalle.dogappnew.webservice.ApiService2
import com.univalle.dogappnew.webservice.ApiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AppointmentRepository(private val dao: AppointmentDao) {
    private var apiService: ApiService = ApiUtils.getApiService()
    private var apiService2: ApiService2 = ApiUtils.getApiService2()
    //private var appoinmentDao: AppointmentDao = AppointmentDB.getDatabase(context).appointmentDao()


    fun insert(appointment: Appointment) {
        Thread {
            dao.insertAppointment(appointment)
        }.start()
    }

    suspend fun getAllAppointments(): MutableList<Appointment> {
        return withContext(Dispatchers.IO){
            dao.getAllAppointments()
        }
    }

    suspend fun getAppointmentById(id: Int): Appointment? {
        return withContext(Dispatchers.IO) {
            dao.getAppointmentById(id) //
        }
    }

    fun deleteAppointment(id: Int) {
        Thread {
            dao.deleteAppointment(id)
        }.start()
    }


    suspend fun getRazas(): List<String>{
        return withContext(Dispatchers.IO){
            try{
                val response = apiService.getRazas()
                response.message
            }catch (e: Exception){
                e.printStackTrace()
                emptyList()
            }
        }
    }

    suspend fun getAllBreeds(): List<String>{
        return withContext(Dispatchers.IO){
            try{
                val response = apiService.getBreedsListAll()
                response.message.keys.toList()
            }catch (e: Exception){
                e.printStackTrace()
                emptyList()
            }
        }
    }



    suspend fun getImageByBreed(breed: String): String {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService2.getImageByBreed(breed.lowercase())
                response.message // URL de la imagen
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }
    }

    fun updateAppointment(appointment: Appointment) {
        Thread {
            dao.updateAppointment(
                appointment.id,
                appointment.nombreMascota,
                appointment.raza,
                appointment.nombrePropietario,
                appointment.telefono,
                appointment.foto


            )
        }.start()
    }



}
