package com.example.sistemaventilacion.dataclass

import com.example.sistemaventilacion.dataclass.ControlTempC

data class SensorPocket(
    val tempC: Float, // Variables de temperatura en Celsius
    val hum: Float, // Variables de Humedad en %
    val counter: UInt // Contador no se para que sirve?
){
    constructor(): this(13F,12F, 0u)
}


