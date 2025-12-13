package com.example.sistemaventilacion.data.remote.firebase

import android.util.Log
import com.example.sistemaventilacion.dataclass.ActionType
import com.example.sistemaventilacion.dataclass.History
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class HistoryRepository {

    private val database = Firebase.database
    private val historialRef = database.getReference("historial")

    fun logAction(history: History) {
        historialRef.child("userId")
            .push()
            .setValue(history)
            .addOnFailureListener { e ->
                Log.e("HistoryRepository", "Error al registrar auditoría en Firebase", e)
            }
    }

    fun getAuditLogs(userId: String, limitToLast: Int = 50): Flow<List<History>> = callbackFlow {
        Log.d("HistoryRepository", "Iniciando getAuditLogs desde la ruta fija 'historial/userId'")

        val query = historialRef
            .child("userId")
            .orderByChild("timestamp")
            .limitToLast(limitToLast)

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    Log.w("HistoryRepository", "No existen datos en la ruta 'historial/userId'")
                    trySend(emptyList())
                    return
                }

                val logs = snapshot.children.mapNotNull { data ->
                    try {
                        data.getValue(History::class.java)?.copy(id = data.key ?: "")
                    } catch (e: Exception) {
                        Log.e("HistoryRepository", "Error al deserializar un log", e)
                        null
                    }
                }.sortedByDescending { it.timestamp }

                Log.d("HistoryRepository", "Se encontraron ${logs.size} logs en 'historial/userId'")
                trySend(logs)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("HistoryRepository", "Lectura cancelada para 'historial/userId'", error.toException())
                close(error.toException())
            }
        }
        query.addValueEventListener(listener)

        awaitClose {
            Log.d("HistoryRepository", "Cerrando listener para 'historial/userId'")
            query.removeEventListener(listener)
        }
    }

    fun postAuditLog(
        userId: String,
        userName: String,
        action: String,
        actionType: ActionType,
        previousValue: String? = null,
        newValue: String? = null
    ) {
        val log = History(
            userId = userId,
            userName = userName,
            action = action,
            actionType = actionType,
            previousValue = previousValue,
            newValue = newValue
        )
        logAction(log)
    }
}