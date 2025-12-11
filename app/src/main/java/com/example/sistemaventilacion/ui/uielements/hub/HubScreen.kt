package com.example.sistemaventilacion.ui.uielements.hub

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.ui.uielements.composables.AuthImageLogo
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar
import com.example.sistemaventilacion.ui.uielements.composables.ColumnContent
import com.example.sistemaventilacion.ui.uielements.composables.TopBar

@Composable
fun HubScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBar(
                navController,
                "HubScreen",
                "Auth",
                loginRoute = true,
                canGoBack = true,
                inclusivePop = false
            )
        },
        bottomBar = {
            BottomAppBar(
                navController = navController,
                selected = "Hub",
                onSelected = { navController.navigate(it) })
        }
    ) { paddingValues ->
        HubStructure(navController, modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun HubStructure(navController: NavHostController, modifier: Modifier) {
    // La lógica de HubStructure se simplifica: ya no maneja el estado del diálogo modal.

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        AuthImageLogo()
        Text("Bienvenido", style = MaterialTheme.typography.titleMedium)
        Text("ZeusApp", style = MaterialTheme.typography.headlineSmall)
        Text("¿Qué desea hacer?", style = MaterialTheme.typography.titleSmall)
        Text(
            "Seleccione una opción para configurar el sistema",
            style = MaterialTheme.typography.bodySmall
        )

        // Se llama a ColumnContent, la cual maneja la navegación directa.
        ColumnContent(
            navController = navController,
            // Esta lambda ya no es funcionalmente relevante, ya que todas las opciones navegan.
            onOptionClicked = { /* No hacer nada */ }
        )
    }
}