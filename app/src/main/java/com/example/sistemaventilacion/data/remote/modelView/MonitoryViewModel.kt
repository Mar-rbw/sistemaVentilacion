package com.example.sistemaventilacion.data.remote.modelView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sistemaventilacion.data.remote.firebase.MonitoryRepository
import com.example.sistemaventilacion.dataclass.SensorPocket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MonitoryViewModel(
    private val repository: MonitoryRepository = MonitoryRepository()
): ViewModel() {
    private val _sensorPocket = MutableStateFlow(SensorPocket())
    val sensorData = _sensorPocket.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getSensorPocket().collect { data ->
                _sensorPocket.value = data
            }
        }
    }
}



