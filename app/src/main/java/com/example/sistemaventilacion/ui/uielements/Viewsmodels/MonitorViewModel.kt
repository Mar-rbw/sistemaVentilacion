package com.example.sistemaventilacion.ui.uielements.Viewsmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistemaventilacion.dataclass.SensorData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MonitorViewModel : ViewModel() {

    // Estado que la pantalla observa
    private val _sensorData = MutableStateFlow(
        SensorData(
            temperatura = 23.5f,
            humedad = 45f,
            timestamp = System.currentTimeMillis()
        )
    )
    val sensorData: StateFlow<SensorData> = _sensorData

    init {
        // TEMPORAL: Simulación de actualizaciones cada 5 segundos
        viewModelScope.launch {
            while (true) {
                delay(5000)

                // Actualizamos datos simulados
                val newTemp = (20..30).random() + (0..9).random() / 10f
                val newHum = (40..60).random().toFloat()

                _sensorData.value = SensorData(
                    temperatura = newTemp,
                    humedad = newHum,
                    timestamp = System.currentTimeMillis()
                )
            }
        }
    }
}
