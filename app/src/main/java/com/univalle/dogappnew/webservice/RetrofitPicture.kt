package com.univalle.dogappnew.webservice

import com.univalle.dogappnew.utils.Constants.BASE_URL
import com.univalle.dogappnew.utils.Constants2.BASE_URL1
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitPicture {
    fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL1)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}