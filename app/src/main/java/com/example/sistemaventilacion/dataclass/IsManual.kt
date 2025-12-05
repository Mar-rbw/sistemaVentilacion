package com.example.sistemaventilacion.dataclass

data class IsManual(
    /*
    * True = Manual
    * False = Automático
    */
        val  isManualOn: Boolean = false,
        )