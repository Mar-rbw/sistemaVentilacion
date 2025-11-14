package com.example.sistemaventilacion.data.remote.firebase

import com.example.sistemaventilacion.dataclass.PerfilUsuario
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.database.database

fun RegistrarUsuario(user: PerfilUsuario,
                     onSuccess: () -> Unit = {},
                     onError: (String) -> Unit = {}
){
    val database = Firebase.database
    val usersRef = database.getReference("users")
    usersRef.child(user.usuario).get()
        .addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                onError("El usuario ya existe")
            } else {
                usersRef.child(user.usuario).setValue(user)
                    .addOnSuccessListener { onSuccess }
                    .addOnFailureListener { error -> onError(error.message ?: "Error al registrar") }
            }
        }
        .addOnFailureListener { error -> onError(error.message ?: "Error al acceder a Firebase") }
}