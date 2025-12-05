package com.example.sistemaventilacion.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.ControlScreen
import com.example.myapplication.screens.HistoryScreen
import com.example.sistemaventilacion.ui.uielements.MonitorScreen
import com.example.sistemaventilacion.ui.uielements.ActivacionSistema.ActivacionSistemaScreen
import com.example.sistemaventilacion.ui.uielements.AgendarActivacion.AgendarActivacionScreen
import com.example.sistemaventilacion.ui.uielements.Auth.AuthLoginScreen
import com.example.sistemaventilacion.ui.uielements.Auth.AuthRegisterScreen
import com.example.sistemaventilacion.ui.uielements.Auth.AuthScreen
import com.example.sistemaventilacion.ui.uielements.Hub.HubScreen
import com.example.sistemaventilacion.ui.uielements.NotificacionHumedad.NotificacionHumedadScreen
import com.example.sistemaventilacion.ui.uielements.NotificacionTemperatura.NotificacionTemperaturaScreen

@Composable
fun NavegacionApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "Auth"
    ) {
        composable("Auth") {
            AuthScreen(navController)
        }
        composable("AuthRegister") {
            AuthRegisterScreen(navController)
        }
        composable("AuthLogin") {
            AuthLoginScreen(navController)
        }
        composable("Hub") {
            HubScreen(navController)
        }
        composable("NotificacionHumedad") {
            NotificacionHumedadScreen(navController)
        }
        composable("NotificacionTemperatura") {
            NotificacionTemperaturaScreen(navController)
        }
        composable("ActivacionSistema") {
            ActivacionSistemaScreen(navController)
        }
        composable("AgendarActivacion") {
            AgendarActivacionScreen(navController)
        }
        composable("Control") {
            ControlScreen(navController)
        }
        composable("Historia") {
            HistoryScreen(navController)
        }
        composable("Monitoreo") {
            MonitorScreen(navController)
        }
    }
}