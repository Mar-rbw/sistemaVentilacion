package com.example.sistemaventilacion.dataclass

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface IconSource {
    data class Vector(val imageVector: ImageVector): IconSource
    data class Resource(@DrawableRes val resId: Int): IconSource
}

data class NavChip(
    val route: String,
    val label: String,
    val icon: IconSource,
)