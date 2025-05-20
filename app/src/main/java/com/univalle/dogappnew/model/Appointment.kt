package com.univalle.dogappnew.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appointments")
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreMascota: String,
    val raza: String
)