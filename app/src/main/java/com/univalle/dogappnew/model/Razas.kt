package com.univalle.dogappnew.model

import androidx.core.app.NotificationCompat.MessagingStyle.Message
import com.google.gson.annotations.SerializedName

data class Razas(
    @SerializedName("message")
    val message:String
)
