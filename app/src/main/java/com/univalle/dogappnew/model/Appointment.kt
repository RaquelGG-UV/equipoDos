package com.univalle.dogappnew.model

data class Appointment(
    var id: Int = 0,
    var nombreMascota: String = "",
    var raza: String = "",
    var nombrePropietario: String = "",
    var telefono: String = "",
    var sintomas: String = "",
    var foto: String = ""
) {

    constructor() : this(0, "", "", "", "", "", "")
}