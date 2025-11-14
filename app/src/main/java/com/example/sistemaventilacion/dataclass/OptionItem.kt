package com.example.sistemaventilacion.dataclass

import androidx.compose.runtime.Composable

data class OptionItem(
    val title: String,
    val description: String? = null,
    val icon: @Composable ( () -> Unit)? = null,
    val onClick: () -> Unit
)