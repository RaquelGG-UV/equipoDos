package com.univalle.dogappnew.webservice

import com.univalle.dogappnew.model.Razas
import com.univalle.dogappnew.utils.Constants.END_POINT
import retrofit2.http.GET

interface ApiService {
    @GET(END_POINT)
    suspend fun getRazas(): Razas
}