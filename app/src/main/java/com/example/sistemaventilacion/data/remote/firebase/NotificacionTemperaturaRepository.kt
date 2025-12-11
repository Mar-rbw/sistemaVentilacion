package com.example.sistemaventilacion.data.remote.firebase

import com.example.sistemaventilacion.dataclass.NotificacionTemperatura
import com.google.firebase.Firebase
import com.google.firebase.database.database

fun NotificacionTemperaturaRepository(
    temperatura: NotificacionTemperatura,
    userId: String,
    onResult: (Boolean, String) -> Unit
) {
    val database = Firebase.database
    val temperatureRef = database.getReference("notTem").child(userId)

    temperatureRef.setValue(temperatura)
        .addOnSuccessListener {
            onResult(true, "Temperatura guardada correctamente")
        }
        .addOnFailureListener { error ->
            onResult(false, "Error al guardar la temperatura: ${error.message}")
        }
}