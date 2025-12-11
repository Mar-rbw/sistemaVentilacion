package com.example.sistemaventilacion.ui.uielements.activacionSistema

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.data.remote.firebase.ActivacionRepository
import com.example.sistemaventilacion.dataclass.ActivacionSistema
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar
import com.example.sistemaventilacion.ui.uielements.composables.IconSource
import com.example.sistemaventilacion.ui.uielements.composables.RangeSliderWithTextFields
import com.example.sistemaventilacion.ui.uielements.composables.TextFieldNumberFormatter
import com.example.sistemaventilacion.ui.uielements.composables.TopBar

val RangeTemperatureSaver = Saver<ClosedFloatingPointRange<Float>, List<Float>>(
    save = { listOf(it.start, it.endInclusive) },
    restore = { it[0]..it[1] }
)
val RangeHumeditySaver = Saver<ClosedFloatingPointRange<Float>, List<Float>>(
    save = { listOf(it.start, it.endInclusive) },
    restore = { it[0]..it[1] }
)

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
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}


@Composable
fun ActivacionSistemaStructure(
    navController: NavHostController,
    modifier: Modifier
) {
    val context = LocalContext.current
    var activacionTemperaturaRange by rememberSaveable(stateSaver = RangeTemperatureSaver) {
        mutableStateOf(
            0f..50f
        )
    }
    var activacionHumedadRange by rememberSaveable(stateSaver = RangeHumeditySaver) {
        mutableStateOf(
            0f..50f
        )
    }
    var duracion by rememberSaveable { mutableStateOf("") }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {


        Text("Umbral de Activación")
        Spacer(modifier = Modifier.height(12.dp))
        Text("Configure los parámetros del sistema")
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Parámetros de Activación")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Defina los valores que activarán el sistema automáticamente")
                Spacer(modifier = Modifier.height(8.dp))
                RangeSliderWithTextFields(
                    label = "Humedad de activación(%)",
                    unit = "%",
                    minGlobal = 20f,
                    maxGlobal = 90f,
                    currentRange = activacionHumedadRange,
                    onRangeChange = { activacionHumedadRange = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Nota: Rango válido 20 - 90%")
                Spacer(modifier = Modifier.height(8.dp))
                RangeSliderWithTextFields(
                    label = "Temperatura de activación",
                    unit = "°C",
                    minGlobal = 0f,
                    maxGlobal = 50f,
                    currentRange = activacionTemperaturaRange,
                    onRangeChange = { activacionTemperaturaRange = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Nota: Rango válido 0 - 50°C")
                Spacer(modifier = Modifier.height(8.dp))
                TextFieldNumberFormatter(
                    value = duracion,
                    onValueChange = { duracion = it },
                    textLabel = "Duración(minutos)",
                    textInput = "Ej:180(3 horas)",
                    iconSource = IconSource.Vector(Icons.Default.Timer),
                    descripcion = "Duración",
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Nota: La duración debe ser en minutos")
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        val minTem = activacionTemperaturaRange.start
                        val maxTem = activacionTemperaturaRange.endInclusive

                        val minHum = activacionHumedadRange.start
                        val maxHum = activacionHumedadRange.endInclusive

                        val duracion = duracion.toInt()
                        val activado = true


                        val activation = ActivacionSistema(
                            MIN_TEM = minTem,
                            MAX_TEM = maxTem,
                            MIN_HUM = minHum,
                            MAX_HUM = maxHum,
                            DURACION = duracion,
                            ACTIVADO = activado
                        )
                        val userId = "ID_DEL_USUARIO"

                        ActivacionRepository(
                            userId = userId,
                            activacion = activation,
                            onResult = { success, message ->
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF76B1C),
                        contentColor = Color.White,
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                {
                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = "Guardar",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Activar Configuración")
                }
            }
        }
    }
}

