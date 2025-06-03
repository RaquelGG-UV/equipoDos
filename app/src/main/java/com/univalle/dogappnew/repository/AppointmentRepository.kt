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

    val allAppointments = dao.getAllAppointments()

    fun insert(appointment: Appointment) {
        Thread {
            dao.insertAppointment(appointment)
        }.start()
    }

//    suspend fun getAllAppointments()MutableList<Appointment>{
//        return withContext(Dispatchers.IO){
//            appoinmentDao.getAllAppointments()
//        }
//    }

    suspend fun getAllAppointments(): MutableList<Appointment> {
        return withContext(Dispatchers.IO){
            dao.getAllAppointments()
        }
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

//    suspend fun getPicture(): List<String>{
//        return withContext(Dispatchers.IO){
//            try{
//                val response = apiService2.getPicture()
//                response.message
//            }catch (e: Exception){
//                e.printStackTrace()
//                emptyList()
//            }
//        }
//    }

}
