package com.example.sistemaventilacion.ui.uielements.agendarActivacion

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.data.remote.firebase.AgendarRepository
import com.example.sistemaventilacion.dataclass.AgendarActivacion
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar
import com.example.sistemaventilacion.ui.uielements.composables.DatePicker
import com.example.sistemaventilacion.ui.uielements.composables.IconSource
import com.example.sistemaventilacion.ui.uielements.composables.TextFieldNumberFormatter
import com.example.sistemaventilacion.ui.uielements.composables.TimePicker
import com.example.sistemaventilacion.ui.uielements.composables.TopBar
import java.util.Locale


@Composable
fun AgendarActivacionScreen(
    navController: NavHostController,
) {
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
        AgendarActivacionStructure(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun AgendarActivacionStructure(
    navController: NavHostController,
    modifier: Modifier
) {

    val context = LocalContext.current

    var duracionState by rememberSaveable { mutableStateOf("") }
    var dateState by rememberSaveable { mutableStateOf("") }
    var timeState by rememberSaveable { mutableStateOf("") }

    val onDateSelected: (String) -> Unit = { newDate ->
        dateState = newDate
    }
    val onTimeSelected: (String) -> Unit = { newTime ->
        timeState = newTime
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text("Agendar Activación", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Programe la activación automática", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Programar Activación", style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Configure fecha, hora y duración",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    DatePicker(
                        onDateSelected = onDateSelected,
                        selectedDate = dateState
                    )
                    TimePicker(
                        onTimeSelected = onTimeSelected,
                        selectedTime = timeState
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))


                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Duración de la Tarea",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Defina el tiempo de operación del sistema (minutos).",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    TextFieldNumberFormatter(
                        value = duracionState,
                        onValueChange = { duracionState = it },
                        textLabel = "Duración (minutos)",
                        textInput = "Ej: 180 (3 horas)",
                        iconSource = IconSource.Vector(Icons.Default.Timer),
                        descripcion = "Duración",
                        modifierTextField = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        val duracionMin = duracionState.toIntOrNull()


                        if (dateState.isEmpty() || timeState.isEmpty()) {
                            Toast.makeText(context, "Por favor, seleccione fecha y hora.", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        if (duracionMin == null || duracionMin <= 0) {
                            Toast.makeText(
                                context,
                                "Por favor, ingrese una duración válida (mayor a 0).",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        }

                        val message = String.format(
                            Locale.getDefault(),
                            "Tarea agendada: %s a las %s por %d min. Enviando a Firebase...",
                            dateState,
                            timeState,
                            duracionMin
                        )
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()

                        val activado = true
                        val date: String = dateState
                        val time: String = timeState
                        val duracion: Int = duracionMin

                        val agendar = AgendarActivacion(
                            ACTIVADO = activado,
                            FECHA = date,
                            HORA = time,
                            DURACION = duracion
                        )
                        val userId = "ID_DEL_USUARIO"


                        AgendarRepository(
                            agendar = agendar,
                            userId = userId,
                            onResult = { success, message ->
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = dateState.isNotEmpty() &&
                            timeState.isNotEmpty() &&
                            duracionState.toIntOrNull() != null && duracionState.toInt() > 0
                ) {
                    Text("Agendar Tarea")
                }
            }
        }
    }
}