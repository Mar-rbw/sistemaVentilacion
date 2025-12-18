package com.example.sistemaventilacion.dataclass

data class NotificacionTemperatura(
    val MIN_TEM: Float,
    val MAX_TEM: Float
) {
    constructor() : this(0F, 0F)
}
