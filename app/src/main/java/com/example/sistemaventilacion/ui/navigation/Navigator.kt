package com.example.sistemaventilacion.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.ControlScreen
import com.example.sistemaventilacion.ui.uielements.HistoryScreen
//import com.example.sistemaventilacion.ui.uielements.MonitorScreen
import com.example.sistemaventilacion.ui.uielements.activacionSistema.ActivacionSistemaScreen
import com.example.sistemaventilacion.ui.uielements.agendarActivacion.AgendarActivacionScreen
import com.example.sistemaventilacion.ui.uielements.auth.AuthLoginScreen
import com.example.sistemaventilacion.ui.uielements.auth.AuthRegisterScreen
import com.example.sistemaventilacion.ui.uielements.auth.AuthScreen
import com.example.sistemaventilacion.ui.uielements.hub.HubScreen
import com.example.sistemaventilacion.ui.uielements.notificacionHumedad.NotificacionHumedadScreen
import com.example.sistemaventilacion.ui.uielements.notificacionHumedad.NotificacionTemperaturaScreen

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
//        composable("Monitoreo") {
//            MonitorScreen(navController)
//        }
    }
}