package com.example.sistemaventilacion.dataclass

data class SensorData(
    val temperatura: Float =0f,
    val humedad:Float =0f,
    val  timestamp: Long
)