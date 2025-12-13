package com.example.sistemaventilacion.dataclass

data class PasswordReset(
    val isLoading: Boolean = false,
    val isSent: Boolean = false,
    val error: String? = null
)
