package com.univalle.dogappnew.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://dog.ceo/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val dogApiService: DogApiService by lazy {
        retrofit.create(DogApiService::class.java)
    }

    val breedsApiService: BreedsApiService by lazy {
        retrofit.create(BreedsApiService::class.java)
    }

    val razasApiService: RazasApiService by lazy {
        retrofit.create(RazasApiService::class.java)
    }
}