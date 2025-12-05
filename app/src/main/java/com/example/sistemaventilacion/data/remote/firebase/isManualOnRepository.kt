package com.example.sistemaventilacion.data.remote.firebase

import com.google.firebase.Firebase
import com.google.firebase.database.database

class IsManualOnRepository {
    private val database = Firebase.database
    private val manualRef = database.getReference("control/state")

    fun getIsManualOn(
        onResult: (Boolean) -> Unit,
        onError: (String) -> Unit = {}
    ) {
        manualRef.get()
            .addOnSuccessListener { snap ->
                val value = snap.getValue(Boolean::class.java) ?: false
                onResult(value)
            }
            .addOnFailureListener { e ->
                onError(e.message ?: "Error al leer estado")
            }
    }

    fun setIsManualOn(
        value: Boolean,
        onSuccess: () -> Unit,
        onError: (String) -> Unit = {}
    ) {
        manualRef.setValue(value)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onError(e.message ?: "Error al actualizar estado")
            }
    }
}