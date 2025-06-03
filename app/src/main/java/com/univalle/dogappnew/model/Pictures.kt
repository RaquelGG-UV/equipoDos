package com.univalle.dogappnew.model

import com.google.gson.annotations.SerializedName

data class Pictures(
    @SerializedName("message")
    val message: String
)