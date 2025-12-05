package com.example.sistemaventilacion.data.remote.firebase

import com.example.sistemaventilacion.dataclass.SensorPocket
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class MonitoryRepository {
    val database = Firebase.database
    val monitorsRef = database.getReference("monitor")

    fun getSensorPocket(): Flow<SensorPocket> = callbackFlow {

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val temperatura = snapshot.child("tempC").getValue(Double::class.java) ?: 0.0
                val humedad = snapshot.child("hum").getValue(Double::class.java) ?: 0.0
                val counter = snapshot.child("counter").getValue(Int::class.java) ?: 0

                trySend(
                    SensorPocket(
                        temperatura,
                        humedad, counter
                    )
                )
            }

            override fun onCancelled(error: DatabaseError) {}
        }

        monitorsRef
            .child("sensores")
            .child("sensor1")
            .addValueEventListener(listener)

        awaitClose {
            monitorsRef
                .child("sensores")
                .child("/sensor01")
                .removeEventListener(listener)
        }
    }
}