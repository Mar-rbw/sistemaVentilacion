package com.example.sistemaventilacion.data.remote.firebase

import com.example.sistemaventilacion.dataclass.AgendarActivacion
import com.google.firebase.Firebase
import com.google.firebase.database.database

fun AgendarRepository(
    agendar: AgendarActivacion,
    userId: String,
    onResult: (Boolean, String) -> Unit
) {
    val database = Firebase.database
    val agendarRef = database.getReference("agendar").child(userId)

    agendarRef.setValue(agendar)
        .addOnSuccessListener {
            onResult(true, "Agendación guardada correctamente")
        }
        .addOnFailureListener { error ->
            onResult(false, "Error al Agendar: ${error.message}")
        }
}