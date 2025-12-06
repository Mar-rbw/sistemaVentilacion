package com.example.sistemaventilacion.ui.uielements.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.ui.uielements.composables.AuthImageLogo
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar
import com.example.sistemaventilacion.ui.uielements.composables.TopBar

@Composable
fun AuthScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBar(
                navController,
                "AuthScreen",
                "Auth",
                loginRoute = false,
                canGoBack = false,
            )
        },
        bottomBar = {
            BottomAppBar(
                navController = navController,
                selected = "Auth",
                onSelected = { navController.navigate(it) }
            )
        }
    ) { paddingValues ->
        AuthStructure(navController, modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun AuthStructure(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        AuthImageLogo()
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "ZeusApp",
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Text(
            text = "Ingrese sus credenciales para acceder al sistema"
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Button(onClick = { navController.navigate("AuthRegister") }) {
            Text("Registrarse")
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Button(onClick = { navController.navigate("AuthLogin") }) {
            Text("Iniciar Sesión")
        }
    }
}