package com.example.sistemaventilacion.dataclass

data class NotificacionHumedad(
    val MIN_HUM: Float,
    val MAX_HUM: Float
) {
    constructor() : this(13F, 12F)
}