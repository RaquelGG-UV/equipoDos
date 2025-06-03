package com.univalle.dogappnew.webservice

import com.univalle.dogappnew.model.Pictures
import com.univalle.dogappnew.utils.Constants2.END_POINT1
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService2 {
    @GET(END_POINT1)
    suspend fun getImageByBreed(@Path("breed") breed: String): Pictures
}