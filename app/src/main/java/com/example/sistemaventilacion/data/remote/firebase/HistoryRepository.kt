package com.example.sistemaventilacion.data.remote.firebase

import com.example.sistemaventilacion.dataclass.History
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
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
        historialRef.child(history.userId)
            .push()
            .setValue(history)
            .addOnFailureListener { e ->
                println("Error al registrar auditoría en Firebase: ${e.message}")
            }
    }

    fun getAuditLogs(userId: String, limitToLast: Int = 50): Flow<List<History>> = callbackFlow {
        val query = historialRef
            .child(userId)
            .orderByChild("timestamp")
            .limitToLast(limitToLast)

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val logs = snapshot.children
                    .mapNotNull { snapshot ->
                        snapshot.getValue(History::class.java)?.let { history ->
                            history.copy(id = snapshot.key ?: "")
                        }
                    }
                    .sortedByDescending { it.timestamp }

                trySend(logs)
            }

            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        query.addValueEventListener(listener)

        awaitClose {
            query.removeEventListener(listener)
        }
    }
}