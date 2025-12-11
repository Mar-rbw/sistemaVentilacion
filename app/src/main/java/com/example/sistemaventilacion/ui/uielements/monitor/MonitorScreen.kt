package com.example.sistemaventilacion.ui.uielements.monitor

import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Date
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.font.FontWeight
import com.example.sistemaventilacion.data.remote.modelView.MonitoryViewModel
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar
import com.example.sistemaventilacion.ui.uielements.composables.TopBar

@Composable
fun MonitorScreen(navController: NavController, viewModel: MonitoryViewModel = viewModel()) {

    val context = LocalContext.current
    val data = viewModel.sensorData.collectAsState()

    val TEMP_LIMITE = 23.5f
    val HUM_LIMITE_INFERIOR = 40.0f
    val HUM_LIMITE_SUPERIOR = 60.0f
    val COLOR_ALERTA = Color(0xFFF44336)
    val COLOR_CONFORT = Color(0xFF43A047)
    val COLOR_PRIMARIO = Color(0xFF1976D2)

    Scaffold(
        topBar = {
            TopBar(
                navController,
                "MonitoreoScreen",
                "Hub",
                loginRoute = true,
                canGoBack = true
            )
        },
        bottomBar = {
            BottomAppBar(
                navController = navController,
                selected = "Monitor",
                onSelected = { navController.navigate(it) })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Icon(
                imageVector = Icons.Default.Air,
                contentDescription = "Ventilación",
                tint = COLOR_PRIMARIO,
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Thermostat,
                    contentDescription = "Temperatura",
                    tint = COLOR_PRIMARIO,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = "Temperatura Actual", fontSize = 16.sp, color = Color.Gray)
                    Text(
                        text = "${data.value.temperatura} °C",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        if (data.value.temperatura < TEMP_LIMITE) "Temperatura baja" else "Temperatura confortable",
                        color = if (data.value.temperatura < TEMP_LIMITE) COLOR_ALERTA else COLOR_CONFORT
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))


            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.WaterDrop,
                    contentDescription = "Humedad",
                    tint = COLOR_PRIMARIO,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(horizontalAlignment = Alignment.Start) {
                    Text(text = "Humedad Actual", fontSize = 16.sp, color = Color.Gray)
                    Text(
                        text = "${data.value.humedad}%",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        if (data.value.humedad < HUM_LIMITE_INFERIOR) {
                            "Humedad baja"
                        } else if (data.value.humedad > HUM_LIMITE_SUPERIOR) {
                            "Humedad alta"
                        } else {
                            "Humedad óptima"
                        },
                        color = if (data.value.humedad < HUM_LIMITE_INFERIOR || data.value.humedad > HUM_LIMITE_SUPERIOR) {
                            COLOR_ALERTA
                        } else {
                            COLOR_CONFORT
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Actualizado: ${Date(data.value.timestamp)}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}