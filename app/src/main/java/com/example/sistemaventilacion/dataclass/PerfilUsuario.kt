package com.example.sistemaventilacion.dataclass

data class PerfilUsuario (
    val id: String, // id del usuario
    val usuario: String,  // usuario que puede ser su nombre del empleado o nombre personalizado
    val contrasenia: String, // Contraseña segura del usuario
) {
    constructor(): this("0","","")
}