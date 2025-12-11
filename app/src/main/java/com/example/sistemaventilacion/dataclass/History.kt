package com.example.sistemaventilacion.dataclass

import com.google.firebase.database.Exclude

data class History(
    // ID usado localmente/por Firebase, puede excluirse de la serialización si Firebase genera la clave
    @get:Exclude val id: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val userId: String = "",
    val userName: String = "",
    val action: String = "",
    val actionType: ActionType = ActionType.OTHER,
    val previousValue: String? = null,
    val newValue: String? = null,
) {
    constructor() : this(action = "")
}

enum class ActionType {
    REGISTER,
    LOGIN,
    MODIFY_PASSWORD,
    LOGOUT,
    ERROR,
    OTHER,
    /* Acciones de Configuración y Control */
    CONFIG_CHANGE,
    /*Acciones de Tareas especifico*/
    TASK_MANUAL_CONTROL,
    TASK_SCHEDULED,
    TASK_DELETED
}


