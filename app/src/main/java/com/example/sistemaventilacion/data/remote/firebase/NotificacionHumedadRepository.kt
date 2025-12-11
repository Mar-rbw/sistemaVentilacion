package com.example.sistemaventilacion.data.remote.firebase

import com.example.sistemaventilacion.dataclass.NotificacionHumedad
import com.google.firebase.Firebase
import com.google.firebase.database.database


fun notificacionHumedadRepository(
    humedad: NotificacionHumedad,
    userId: String,
    onResult: (Boolean, String) -> Unit
) {
    val database = Firebase.database
    val humidityRef = database.getReference("notHum").child(userId)

    humidityRef.setValue(humedad)
        .addOnSuccessListener {
            onResult(true, "Humedad guardada correctamente")
        }
        .addOnFailureListener { error ->
            onResult(false, "Error al guardar la humedad: ${error.message}")
        }
}