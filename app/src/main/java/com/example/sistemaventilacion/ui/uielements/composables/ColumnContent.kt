package com.example.sistemaventilacion.ui.uielements.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Monitor
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.SwitchLeft
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.R
import com.example.sistemaventilacion.dataclass.OptionItem


@Composable
fun ColumnContent(
    navController: NavHostController,
    onOptionClicked: (OptionItem) -> Unit
) {
    val ventilacionOptions = remember {
        listOf(
            OptionItem(
                title = "Activación Inmediata",
                description = "Encender/Apagar el sistema de ventilación.",
                icon = { Icon(Icons.Default.PowerSettingsNew, contentDescription = "Encender/Apagar", tint = Color(0xFFF76B1C)) },
                onClick = { navController.navigate("ActivacionSistema") }
            ),
            OptionItem(
                title = "Agendar Tarea",
                description = "Programar encendido y apagado automáticos.",
                icon = { Icon(Icons.Default.CalendarMonth, contentDescription = "Calendario", tint = Color(0xFFF76B1C)) },
                onClick = { navController.navigate("AgendarActivacion") }
            ),
            OptionItem(
                title = "Revisar modo de uso",
                description = "Cambiar el modo de uso del sistema.",
                icon = { Icon(Icons.Default.SwitchLeft, contentDescription = "Modo de Uso", tint = Color(0xFFF76B1C)) },
                onClick = { navController.navigate("Control") }
            ),
            OptionItem(
                title = "Monitorear ventilación",
                description = "Supervisa la operación del sistema de ventilación.",
                icon = { Icon(Icons.Default.Monitor, contentDescription = "Monitoreo", tint = Color(0xFFF76B1C)) },
                onClick = { navController.navigate("Monitoreo") }
            )
        )
    }
    val notificacionesOptions = remember {
        listOf(
            OptionItem(
                "Notificación de Temperatura",
                "Crear una notificación de temperatura, en caso de superar un umbral",
                icon = { Icon(Icons.Default.Thermostat, contentDescription = "Temperatura", tint = Color(0xFFF76B1C)) },
                onClick = {navController.navigate("NotificacionTemperatura")}
            ),
            OptionItem(
                "Notificación de Humedad",
                "Crear una notificación de humedad, en caso de superar un umbral",
                icon = { Icon(painterResource(R.drawable.outline_humidity_percentage_24), contentDescription = "Humedad", tint = Color(0xFFF76B1C)) },
                onClick = {navController.navigate("NotificacionHumedad")}
            ),
            OptionItem(
                "Historial acciones",
                "Revisa el historial de acciones por medio de logs",
                icon = { Icon(Icons.Default.Monitor, contentDescription = "Historial", tint = Color(0xFFF76B1C)) },
                onClick = {navController.navigate("Historial")}
            )
        )
    }


    ButtonExpansibleWithOptions(
        title = "Control de Ventilación",
        subtitle = "Configurar modos y horarios de operación.",
        icon = R.drawable.logozeusair,
        options = ventilacionOptions,
        onOptionClicked = onOptionClicked
    )

    Spacer(modifier = Modifier.height(16.dp))

    ButtonExpansibleWithOptions(
        title = "Notificaciones y Alarmas",
        subtitle = "Ajustar umbrales de temperatura y humedad.",
        icon = R.drawable.logozeusair,
        options = notificacionesOptions,
        onOptionClicked = onOptionClicked
    )
}