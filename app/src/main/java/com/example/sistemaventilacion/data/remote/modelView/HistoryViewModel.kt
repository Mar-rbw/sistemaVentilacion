package com.example.sistemaventilacion.data.remote.modelView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistemaventilacion.data.remote.firebase.HistoryRepository
import com.example.sistemaventilacion.dataclass.History
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val historyRepository: HistoryRepository = HistoryRepository()
) : ViewModel() {
    private val currentUserId: String = "ID_DEL_USUARIO_ACTUAL"
    private val logLimit = 50

    val auditLogs: StateFlow<List<History>> = historyRepository
        .getAuditLogs(
            userId = currentUserId,
            limitToLast = logLimit
        )
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    fun registerLog(log: History) {
        viewModelScope.launch {
            val finalLog = log.copy(userId = currentUserId)
            historyRepository.logAction(finalLog)
        }
    }
}