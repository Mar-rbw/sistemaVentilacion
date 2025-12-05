package com.example.sistemaventilacion.data.remote.firebase

import com.example.sistemaventilacion.dataclass.PerfilUsuario
import com.example.sistemaventilacion.dataclass.SensorPocket
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

fun Monitorear(
    monitor: String,
    temperatura: Float,
    humedad: Float,
    counter: UInt,
    onResult: (Boolean, String) -> Unit
) {
    val database = Firebase.database
    val sensorPocketRef = database.getReference("monitor")


        val usersRef = database.getReference("users")

        sensorPocketRef.child("monitor").get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val sensorPocket = snapshot.getValue(SensorPocket::class.java)
                    if (sensorPocket?.tempC != null) {
                        onResult(true, "${sensorPocket.tempC}")
                    } else {
                        onResult(false, "Temperatura no encontrada")
                    }

                } else {
                    onResult(false, "Monitoreo no encontrado")
                }
            }
            .addOnFailureListener {
                onResult(false, "Error de conexión con Firebase")
            }
    }