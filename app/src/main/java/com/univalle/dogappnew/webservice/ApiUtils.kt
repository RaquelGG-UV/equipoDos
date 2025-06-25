package com.univalle.dogappnew.webservice

class ApiUtils {
    companion object {
        fun getApiService(): ApiService {
            return RetrofitClient.apiService
        }

        fun getApiService2(): ApiService2 {
            return RetrofitClient.apiService2
        }
    }
}