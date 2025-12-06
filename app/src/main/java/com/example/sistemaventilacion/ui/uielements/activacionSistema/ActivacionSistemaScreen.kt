package com.example.sistemaventilacion.ui.uielements.activacionSistema

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.ui.uielements.composables.TopBar
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.foundation.text.KeyboardOptions
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar

@Composable
fun ActivacionSistemaScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                title = "ActSisScreen",
                "Hub",
                loginRoute = true,
                canGoBack = true
            )
        },
        bottomBar = {
            BottomAppBar(
                navController = navController,
                selected = "ActivacionSistema",
                onSelected = { navController.navigate(it) }
            )
        }
    ) { paddingValues ->
        ActivacionSistemaStructure(
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun ActivacionSistemaStructure(modifier: Modifier) {
    val context = LocalContext.current

    var humedadState by rememberSaveable { mutableStateOf("") }
    var temperaturaState by rememberSaveable { mutableStateOf("") }
    var duracionState by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Header()
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                FormularioText(
                    title = "Humedad",
                    subtitle = "Defina el umbral de humedad para la activación."
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextFieldNumberFormatter(
                    value = humedadState,
                    onValueChange = { humedadState = it },
                    textLabel = "Humedad de Activación (%)",
                    textInput = "Ej: 75"
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormularioText(
                    title = "Temperatura",
                    subtitle = "Defina el umbral de temperatura para la activación."
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextFieldNumberFormatter(
                    value = temperaturaState,
                    onValueChange = { temperaturaState = it },
                    textLabel = "Temperatura de Activación (°C)",
                    textInput = "Ej: 28"
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormularioText(
                    title = "Duración",
                    subtitle = "Tiempo de operación del sistema una vez activado (minutos)."
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextFieldNumberFormatter(
                    value = duracionState,
                    onValueChange = { duracionState = it },
                    textLabel = "Duración de Operación (min)",
                    textInput = "Ej: 30"
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ButtonBackDefault(
                onClick = {
                    humedadState = "70"
                    temperaturaState = "25"
                    duracionState = "15"
                    Toast.makeText(
                        context,
                        "Valores por defecto cargados",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
            ButtonSaveField(
                onClick = {
                    val hasAllValues = humedadState.isNotBlank() &&
                            temperaturaState.isNotBlank() &&
                            duracionState.isNotBlank()

                    if (hasAllValues) {
                        Toast.makeText(
                            context,
                            "Configuración Guardada: H:$humedadState%, T:$temperaturaState°C, D:$duracionState min",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Por favor complete todos los campos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                enabled = humedadState.isNotBlank() &&
                        temperaturaState.isNotBlank() &&
                        duracionState.isNotBlank()
            )
        }
    }
}

@Composable
fun Header() {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(
            text = "Umbral de Activación",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = "Configure los parámetros que activarán el sistema de ventilación.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun FormularioText(title: String, subtitle: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ButtonSaveField(
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = Modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        enabled = enabled
    ) {
        Text("Guardar Configuración")
    }
}

@Composable
fun ButtonBackDefault(onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .padding(end = 16.dp)
            .height(56.dp)
    ) {
        Text("Valores por Defecto")
    }
}

@Composable
fun TextFieldNumberFormatter(
    value: String,
    onValueChange: (String) -> Unit,
    textLabel: String,
    textInput: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            // Permite números, punto decimal y signo negativo
            if (newValue.all { it.isDigit() || it == '.' || it == '-' } || newValue.isEmpty()) {
                onValueChange(newValue)
            }
        },
        label = { Text(textLabel) },
        placeholder = { Text(textInput) },
        leadingIcon = {
            Icon(Icons.Default.Info, contentDescription = "Parámetro")
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        ),
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}