package com.univalle.dogappnew.webservice

import retrofit2.Response
import retrofit2.http.GET

data class BreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)

interface ApiService {
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): Response<BreedsResponse>
}