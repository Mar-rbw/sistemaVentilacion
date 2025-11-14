package com.example.sistemaventilacion.ui.uielements.AgendarActivacion

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
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
import com.example.sistemaventilacion.R
import com.example.sistemaventilacion.ui.uielements.composables.ImageElement
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TopAppBar
import com.example.sistemaventilacion.ui.uielements.composables.TextFieldNumberFormatter
import com.example.sistemaventilacion.ui.uielements.composables.TopBar

@Composable
fun AgendarActivacionScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopBar() }
    ) { innerPadding ->
        ActivacionSistemaStructure(Modifier.padding(innerPadding))
    }
}

@Composable
fun ActivacionSistemaStructure(modifier: Modifier) {
    Header()
    Form()
}

@Composable
fun Header(){
    Text("Agendar Activación")
    Text("Programe la activación automática")
}

@Composable
fun Form(){
    var duracionState by rememberSaveable { mutableStateOf("") }
    Column {
        Title()
        DatePicker()
        TimePicker()
        TextFieldNumberFormatter(
            duracionState,
            { duracionState = it },
            "Duración",
            "Duración en Minutos",
            R.drawable.time,
            "LogoTime",
            Modifier.fillMaxWidth().padding(16.dp),
            Modifier.fillMaxWidth(),
            Modifier,
            Modifier
        )
    }
}

@Composable
fun Title() {
    ImageElement(
        R.drawable.calendar,
        "CalendarLogo",
    )
    Column {
        Text("Programar Activación")
        Text("Configure fecha, hora y duración")
    }
}

@Composable
fun DatePicker() {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var date by rememberSaveable { mutableStateOf("") }


    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            date = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(java.util.Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
    Column {
        Button(onClick = {datePickerDialog.show()}) {
            Text("Seleccionar fecha")
        }
        Text("Fecha seleccionada: $date")
    }
}

@Composable
fun TimePicker(){
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var time by rememberSaveable { mutableStateOf("") }

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hour, minute ->
            time = String.format("%02d:%02d", hour, minute)
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    Column {
        Button(onClick = { timePickerDialog.show()}) {
            Text("Seleccionar hora")
        }
        Text("Hora seleccionada: $time")
    }
}


