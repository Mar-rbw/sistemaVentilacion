package com.example.sistemaventilacion.ui.uielements.NotificacionTemperatura

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar
import com.example.sistemaventilacion.ui.uielements.composables.TopBar

@Composable
fun NotificacionTemperaturaScreen(navController: NavHostController) {
    Scaffold(topBar = { TopBar(
        navController = navController,
        name = "NotTemScreen",
        nameRoute = "Hub",
        canGoBack = true,
        inclusive = false
    )},
        bottomBar = {BottomAppBar(
            navController = navController
        )}
    ) { paddingValues ->
        NotificacionTemperaturaStructure(navController, modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun NotificacionTemperaturaStructure(navController: NavHostController, modifier: Any) {}

@Composable
fun formHeader(){
    Text("Notificación de Temperatura")
    Text("Defina el umbral para recibir alertas")
}