package com.example.sistemaventilacion.ui.uielements.Monitor

import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sistemaventilacion.utils.SessionManager
import com.example.sistemaventilacion.ui.uielements.Viewsmodels.MonitorViewModel
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.Icons
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Date
import androidx.compose.runtime.collectAsState


@Composable
fun MonitorScreen(navController: NavController, viewModel: MonitorViewModel = viewModel()) {

    val context = LocalContext.current
    val data = viewModel.sensorData.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Monitor") },
                actions = {
                    IconButton(onClick = {
                        SessionManager(context).clearSession()
                        navController.navigate("login") {
                            popUpTo("monitor") { inclusive = true }
                        }
                    }) {
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = "Cerrar sesión",
                            tint = Color.Red
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            /*Icon(
                painter = painterResource(id = /* tu icono */ com.example.sistemaventilacion.R.drawable.ic_ventilador),
                contentDescription = "Ventilación",
                tint = Color(0xFF1976D2),
                modifier = Modifier.size(120.dp)
            ) */

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "${data.value.temperatura} °C", fontSize = 40.sp)
            Text(text = "${data.value.humedad}% Humedad", fontSize = 20.sp, color = Color(0xFF0D47A1))

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Actualizado: ${Date(data.value.timestamp)}",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}
