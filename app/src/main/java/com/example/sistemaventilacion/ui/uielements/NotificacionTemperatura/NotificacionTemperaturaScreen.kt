package com.example.sistemaventilacion.ui.uielements.NotificacionTemperatura

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
        NotificacionTemperaturaStructure(navController)
    }
}

@Composable
fun NotificacionTemperaturaStructure(navController: NavHostController) {}

