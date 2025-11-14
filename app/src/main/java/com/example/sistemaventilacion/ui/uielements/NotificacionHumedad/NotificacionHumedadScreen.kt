package com.example.sistemaventilacion.ui.uielements.NotificacionHumedad

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.R
import com.example.sistemaventilacion.ui.uielements.composables.ImageElement
import com.example.sistemaventilacion.ui.uielements.composables.TopBar
import kotlin.math.roundToInt

@Composable
fun NotificacionHumedadScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                name = "NotHumScreen",
                nameRoute = "Hub",
                canGoBack = true,
                inclusive = false
            )
        }
    ) { paddingValues ->
        NotificacionHumedadStructure(
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun NotificacionHumedadStructure(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Title()
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                FormTitle()
                Spacer(modifier = Modifier.height(16.dp))
                HumidityRangeSlider()
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Guardar Configuración")
        }
    }
}

@Composable
fun Title(){
    Column {
        Text(
            text = "Umbral de Humedad",
        )
        Text(
            text = "Configuración de notificaciones",
        )
    }
}

@Composable
fun FormTitle(){
    Row(verticalAlignment = Alignment.CenterVertically) {
        ImageElement(
            R.drawable.humidity,
            "Icono de humedad",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                "Notificación de Humedad",
            )
            Text(
                "Defina el rango de humedad aceptable (%)",
            )
        }
    }
}

@Composable
fun HumidityRangeSlider() {
    var humidityRange by remember { mutableStateOf(20f..95f) }

    val minRange = 20f
    val maxRange = 95f

    Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Mínimo: ${humidityRange.start.roundToInt()}%",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Máximo: ${humidityRange.endInclusive.roundToInt()}%",
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        RangeSlider(
            value = humidityRange,
            onValueChange = { range ->

                humidityRange = range
            },
            valueRange = minRange..maxRange,
            steps = (maxRange - minRange).roundToInt() - 1,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("${minRange.roundToInt()}%")
            Text("${maxRange.roundToInt()}%")
        }
    }
}