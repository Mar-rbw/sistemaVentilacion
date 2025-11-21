package com.example.sistemaventilacion.dataclass

data class LedState(
    /*
    * True = HIGH = Ventilador apagado
    * False = LOW = Ventilador encendido
    * */
    val ledRojo: Boolean,
    /*
    * True = HIGH = Ventilador encendido
    * False = LOW = Ventilador apagado
    */
    val ledVerde: Boolean
)
