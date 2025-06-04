package com.univalle.dogappnew.webservice

import com.univalle.dogappnew.model.Razas
import com.univalle.dogappnew.utils.Constants.END_POINT
import com.univalle.dogappnew.model.BreedsListResponse
import retrofit2.http.GET

interface ApiService {
    @GET(END_POINT)
    suspend fun getRazas(): Razas

    @GET("breeds/list/all")
    suspend fun getBreedsListAll(): BreedsListResponse
}