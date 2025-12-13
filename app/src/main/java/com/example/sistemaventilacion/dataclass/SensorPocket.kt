package com.example.sistemaventilacion.dataclass

data class SensorPocket(
    val temperatura: Float =0f,
    val humedad:Float =0f,
    val timestamp: Long = 0L,
    val counter: Int? = null
)


