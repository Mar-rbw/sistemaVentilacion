package com.example.sistemaventilacion.dataclass

import com.google.firebase.Timestamp

data class History(
    val id: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val userId: String = "",
    val userName: String = "",
    val action: String = "",
    val actionType: ActionType = ActionType.OTHER,
    val previousValie: String? = null,
    val newValie: String? = null,
    val deviceInfo: String = "",
    val  ipAddess: String? = null
)

enum class ActionType {
 /*Acciones generales*/

    REGISTER,
    LOGIN,
    MODIFIER_PASSWORD,
    LOGOUT,
    ERROR,
    OTHER,

    /*Acciones funcionalidad principal*/
    CONTROL_CHANGE,




}


