package com.example.sistemaventilacion.ui.uielements.composables

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.sistemaventilacion.dataclass.OptionItem

@Composable
fun OptionSettingsCard(option: OptionItem, onClose: () -> Unit) {
    val context = LocalContext.current
    var inputState by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onClose,
        icon = {
            Icon(
                Icons.Default.Warning,
                contentDescription = "Configuración",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = { Text("Configurar: ${option.title}") },
        text = {
            Column {
                Text(option.description ?: "Ajuste la configuración de esta opción.")
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = inputState,
                    onValueChange = { inputState = it },
                    label = { Text("Valor de Configuración") },
                    placeholder = { Text("Ej: 15°C o 7:00 AM") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = {
                        inputState = "Default"
                        Toast.makeText(context, "Valor por defecto cargado", Toast.LENGTH_SHORT).show()
                    }) {
                        Text("Default")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = {
                            if (inputState.isNotBlank()) {
                                Toast.makeText(
                                    context,
                                    "Nueva configuración agregada: $inputState",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        enabled = inputState.isNotBlank()
                    ) {
                        Text("Agregar")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    Toast.makeText(
                        context,
                        "Configuración de ${option.title} guardada: $inputState",
                        Toast.LENGTH_SHORT
                    ).show()
                    onClose()
                },
                enabled = inputState.isNotBlank()
            ) {
                Text("Guardar y Cerrar")
            }
        },
        dismissButton = {
            TextButton(onClick = onClose) {
                Text("Cancelar")
            }
        }
    )
}