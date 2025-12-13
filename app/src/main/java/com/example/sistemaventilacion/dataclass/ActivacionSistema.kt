package com.example.sistemaventilacion.dataclass

data class ActivacionSistema(
    val MIN_TEM: Float,
    val MAX_TEM: Float,
    val MIN_HUM: Float,
    val MAX_HUM: Float,
    val DURACION: Int,
    val ACTIVADO: Boolean
)
{
    constructor(): this(0F,50F,20F,100F,180,false)
}