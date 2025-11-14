package com.example.sistemaventilacion.ui.uielements.AgendarActivacion

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.R
import com.example.sistemaventilacion.ui.uielements.composables.ImageElement
import com.example.sistemaventilacion.ui.uielements.composables.TopBar
import android.widget.Toast


@Composable
fun AgendarActivacionScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                name = "Agendar Tarea",
                nameRoute = "HubScreen",
                canGoBack = true,
                inclusive = false
            )
        }
    ) { innerPadding ->
        ActivacionSistemaStructure(Modifier.padding(innerPadding))
    }
}

@Composable
fun ActivacionSistemaStructure(modifier: Modifier) {
    val context = LocalContext.current

    var duracionState by rememberSaveable { mutableStateOf("") }
    var dateState by rememberSaveable { mutableStateOf("") }
    var timeState by rememberSaveable { mutableStateOf("") }

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
            Form(
                dateState = dateState,
                onDateSelected = { dateState = it },
                timeState = timeState,
                onTimeSelected = { timeState = it },
                duracionState = duracionState,
                onDuracionChange = { duracionState = it }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (dateState.isNotEmpty() && timeState.isNotEmpty() && duracionState.isNotEmpty()) {
                    Toast.makeText(
                        context,
                        "Tarea agendada: ${dateState} a las ${timeState} por ${duracionState} min",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(context, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Agendar Tarea")
        }
    }
}

@Composable
fun Header(){
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(
            "Agendar Activación",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            "Programe la activación automática del sistema de ventilación.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun Form(
    dateState: String,
    onDateSelected: (String) -> Unit,
    timeState: String,
    onTimeSelected: (String) -> Unit,
    duracionState: String,
    onDuracionChange: (String) -> Unit
){
    Column(modifier = Modifier.padding(16.dp)) {
        Title()

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            DatePicker(onDateSelected = onDateSelected, selectedDate = dateState)
            TimePicker(onTimeSelected = onTimeSelected, selectedTime = timeState)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            Text("Duración de la Tarea", style = MaterialTheme.typography.titleMedium)
            Text("Defina el tiempo de operación del sistema (minutos).", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))

            TextFieldNumberFormatter(
                value = duracionState,
                onValueChange = onDuracionChange,
                textLabel = "Duración en Minutos",
                textInput = "Ej: 30"
            )
        }
    }
}

@Composable
fun Title() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        ImageElement(
            R.drawable.calendar,
            "CalendarLogo",
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                "Programar Activación",
            )
            Text(
                "Configure fecha, hora y duración",
            )
        }
    }
}

@Composable
fun DatePicker(onDateSelected: (String) -> Unit, selectedDate: String) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateSelected("$dayOfMonth/${month + 1}/$year")
        },
        calendar.get(Calendar.YEAR),
        calendar.get(java.util.Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedButton(onClick = { datePickerDialog.show() }) {
            Text("Seleccionar fecha")
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(selectedDate.ifEmpty { "Sin fecha" }, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun TimePicker(onTimeSelected: (String) -> Unit, selectedTime: String){
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            onTimeSelected(String.format("%02d:%02d", hour, minute))
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true // 24-hour view
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { timePickerDialog.show()}) {
            Text("Seleccionar hora")
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(selectedTime.ifEmpty { "Sin hora" })
    }
}

@Composable
fun TextFieldNumberFormatter(
    value: String,
    onValueChange: (String) -> Unit,
    textLabel: String, // Etiqueta principal
    textInput: String, // Placeholder
){
    TextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.all {it.isDigit()} || newValue.isEmpty()) {
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