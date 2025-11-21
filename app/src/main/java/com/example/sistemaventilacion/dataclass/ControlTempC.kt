package com.example.sistemaventilacion.dataclass

data class ControlTempC(
    val TEMP_ON_C: Float, /*Temperatura de activación en Celcius*/
    val TEMP_OFF_C: Float /*Temperatura de apagado en Celcius*/
){
    constructor(): this(13F,12F)
}

