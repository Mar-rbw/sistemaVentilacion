package com.example.sistemaventilacion.ui.uielements.ActivacionSistema

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.ui.uielements.NotificacionHumedad.TopBar

@Composable
fun ActivacionSistemaScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopBar() }
    ) { paddingValues ->
        ActivacionSistemaStructure(navController, modifier = Modifier.padding(paddingValues))
    }
}

fun ActivacionSistemaStructure(navController: NavHostController, modifier: Modifier) {}

@Composable
fun Header(){
    Text("Umbral de Activación")
    Text("Configure los parámetros del sistema")
}

@Composable
fun Formulario(){
    var humedadState by rememberSaveable { mutableStateOf("") }
    var temperaturaState by rememberSaveable { mutableStateOf("") }
    var DuracionState by rememberSaveable { mutableStateOf("") }

    FormularioText()
    Spacer(modifier = Modifier.height(8.dp))
    TextFieldNumberFormatter(
        value = humedadState,
        onValueChange = {humedadState = it},
        textLabel = "Humedad de Activación (%)",
        textInput = "0 - 100"
    )
    Spacer(modifier = Modifier.height(8.dp))
    FormularioText()
    Spacer(modifier = Modifier.height(8.dp))
    TextFieldNumberFormatter(
        value = temperaturaState,
        onValueChange = {temperaturaState = it},
        textLabel = "Temperatura de Activación (°C)",
        textInput = "-50 - 100"
    )
    Spacer(modifier = Modifier.height(8.dp))
    FormularioText()
    Spacer(modifier = Modifier.height(8.dp))
    TextFieldNumberFormatter(
        value = humedadState,
        onValueChange = {humedadState = it},
        textLabel = "Humedad de Activación (%)",
        textInput = "0 - 100"
    )
    Spacer(modifier = Modifier.height(8.dp))

}

@Composable
fun FormularioText(){
    Text("Parámetros de Activación")
    Text("Defina los valores que activarán el sistema automáticamente")

}

@Composable
fun TextFieldNumberFormatter(
    value: String,
    onValueChange: (String) -> Unit,
    textLabel: String,
    textInput: String,
    ){
    Column() {
        Text(textLabel)
        TextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.all {it.isDigit()} || newValue.isEmpty()) {
                    onValueChange(newValue)
                }
            },
            label = {Text("Ingrese su $textInput")},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true
        )
    }
}

fun ButtonSaveField(){}

fun ButtonBackDefault(){}