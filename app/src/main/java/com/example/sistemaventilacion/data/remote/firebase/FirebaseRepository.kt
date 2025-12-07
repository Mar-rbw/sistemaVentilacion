package com.example.sistemaventilacion.data.remote.firebase

import com.example.sistemaventilacion.dataclass.SensorData
import com.example.sistemaventilacion.dataclass.EstadoData
import com.google.firebase.database.*

class FirebaseRepository {

    private val db = FirebaseDatabase.getInstance().reference

    fun escucharSensor(callback: (SensorData) -> Unit) {
        db.child("iot/sensor")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue(SensorData::class.java)
                    if (data != null) callback(data)
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    fun escucharEstado(callback: (EstadoData) -> Unit) {
        db.child("iot/estado")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue(EstadoData::class.java)
                    if (data != null) callback(data)
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }
}
