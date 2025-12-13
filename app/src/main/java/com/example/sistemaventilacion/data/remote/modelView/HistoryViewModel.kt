package com.example.sistemaventilacion.data.remote.modelView

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistemaventilacion.data.remote.firebase.HistoryRepository
import com.example.sistemaventilacion.dataclass.History
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModel(
    private val historyRepository: HistoryRepository = HistoryRepository()
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val logLimit = 50

    private val userIdFlow: StateFlow<String?> = callbackFlow<String?> {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser?.uid).isSuccess
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = auth.currentUser?.uid
    )

    val auditLogs: StateFlow<List<History>> = userIdFlow.flatMapLatest { userId ->
        Log.d("HistoryViewModel", "UserID Flow emitió un nuevo valor: $userId")
        // Solo intentamos cargar si el usuario está logueado, aunque el repo use una ruta fija
        if (userId != null) {
            historyRepository.getAuditLogs(userId = userId, limitToLast = logLimit)
        } else {
            flowOf(emptyList())
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun registerLog(log: History) {
        viewModelScope.launch {
            val currentUserId = auth.currentUser?.uid
            val currentUserName = auth.currentUser?.displayName ?: "Usuario del Sistema"

            if (currentUserId != null) {
                val finalLog = log.copy(
                    userId = currentUserId,
                    userName = currentUserName
                )
                historyRepository.logAction(finalLog)
            }
        }
    }
}