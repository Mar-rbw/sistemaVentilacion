package com.example.sistemaventilacion.data.remote.firebase
import com.example.sistemaventilacion.dataclass.PerfilUsuario
import com.google.firebase.Firebase
import com.google.firebase.database.database

fun IniciarSesion(
    usuario: String,
    contrasenia: String,
    onResult: (Boolean, String) -> Unit
) {
    val database = Firebase.database
    val usersRef = database.getReference("users")

    usersRef.child(usuario).get()
        .addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val user = snapshot.getValue(PerfilUsuario::class.java)
                if (user?.contrasenia == contrasenia) {
                    onResult(true, "Bienvenido ${user.usuario}")
                } else {
                    onResult(false, "Contraseña incorrecta")
                }
            } else {
                onResult(false, "Usuario no encontrado")
            }
        }
        .addOnFailureListener {
            onResult(false, "Error de conexión con Firebase")
        }
}