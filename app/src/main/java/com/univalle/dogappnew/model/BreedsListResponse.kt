package com.univalle.dogappnew.model

data class BreedsListResponse(
    val message: Map<String, List<String>>,
    val status: String
)