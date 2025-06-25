package com.univalle.dogappnew.webservice

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

data class Pictures(
    val message: String,
    val status: String
)

interface ApiService2 {
    @GET("breed/{breed}/images/random")
    suspend fun getImageByBreed(@Path("breed") breed: String): Response<Pictures>
}