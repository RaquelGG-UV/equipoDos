package com.univalle.dogappnew.webservice

class ApiUtils {
    companion object{
        fun getApiService():ApiService{
            return RetrofitClient.getRetrofit().create(ApiService::class.java)
        }
        fun getApiService2():ApiService2{
            return RetrofitPicture.getRetrofit().create(ApiService2::class.java)
        }
    }
}