package com.univalle.dogappnew.data.remote

import com.univalle.dogappnew.model.Razas
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

data class DogImageResponse(
    val message: String,
    val status: String
)

data class BreedsResponse(
    val message: Map<String, List<String>>,
    val status: String
)

interface DogApiService {
    @GET("breed/{breed}/images/random")
    suspend fun getRandomImageByBreed(@Path("breed") breed: String): DogImageResponse
}

interface BreedsApiService {
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): Response<BreedsResponse>
}

interface RazasApiService {
    @GET("breeds/list")
    suspend fun getRazas(): Response<Razas>
}