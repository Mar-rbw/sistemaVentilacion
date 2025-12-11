package com.example.sistemaventilacion.dataclass

data class AgendarActivacion(
    val ACTIVADO: Boolean = false,
    val FECHA: String = "",
    val HORA: String = "",
    val DURACION: Int = 0
)
