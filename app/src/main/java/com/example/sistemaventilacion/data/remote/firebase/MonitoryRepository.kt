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

    private val database = Firebase.database
    private val monitorsRef = database.getReference("monitor")


    fun getSensorPocket(): Flow<SensorPocket> = callbackFlow {

        val sensorReference = monitorsRef.child("sensores/sensor1")

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val temperatura = snapshot.child("tempC").getValue(Float::class.java) ?: 0.0f
                val humedad = snapshot.child("hum").getValue(Float::class.java) ?: 0.0f
                val timestamp = snapshot.child("timestamp").getValue(Long::class.java) ?: 0L
                val counter = snapshot.child("counter").getValue(Int::class.java) ?: 0

                trySend(
                    SensorPocket(
                        temperatura,
                        humedad,
                        timestamp,
                        counter,
                    )
                )
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }

        sensorReference.addValueEventListener(listener)

        awaitClose {
            sensorReference.removeEventListener(listener)
        }
    }
}