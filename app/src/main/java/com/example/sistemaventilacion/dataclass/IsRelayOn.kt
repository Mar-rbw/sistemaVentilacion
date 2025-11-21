package com.example.sistemaventilacion.dataclass

data class IsRelayOn(
    /*
    True = LOW = Prender Relé
    False = Apagar Relé
    */
    val isRelayOn: Boolean,
){
    constructor(): this(false)
}
