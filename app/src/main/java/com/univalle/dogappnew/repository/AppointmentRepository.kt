package com.univalle.dogappnew.repository

import com.univalle.dogappnew.data.AppointmentDao
import com.univalle.dogappnew.model.Appointment
import com.univalle.dogappnew.model.Razas
import com.univalle.dogappnew.webservice.ApiService
import com.univalle.dogappnew.webservice.ApiUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AppointmentRepository(private val dao: AppointmentDao) {
    private var apiService: ApiService = ApiUtils.getApiService()

    fun insert(appointment: Appointment) {
        Thread {
            dao.insertAppointment(appointment)
        }.start()
    }

    val allAppointments = dao.getAllAppointments()

    suspend fun getRazas(): MutableList<Razas>{
        return withContext(Dispatchers.IO){
            try{
                val response = apiService.getRazas()
                response
            }catch (e: Exception){
                e.printStackTrace()
                mutableListOf()
            }

        }
    }
}
