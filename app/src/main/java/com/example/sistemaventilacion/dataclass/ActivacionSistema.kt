package com.example.sistemaventilacion.dataclass

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.sistemaventilacion.dataclass.NotificacionTemperatura
import com.example.sistemaventilacion.ui.uielements.activacionSistema.RangeHumeditySaver
import com.example.sistemaventilacion.ui.uielements.activacionSistema.RangeTemperatureSaver

data class ActivacionSistema(
    val MIN_TEM: Float,
    val MAX_TEM: Float,
    val MIN_HUM: Float,
    val MAX_HUM: Float,
    val DURACION: Int,
    val ACTIVADO: Boolean
)
{
    constructor(): this(0F,50F,20F,100F,180,false)
}