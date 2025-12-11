package com.example.sistemaventilacion.ui.uielements.notificacionHumedad

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
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.data.remote.firebase.notificacionHumedadRepository
import com.example.sistemaventilacion.dataclass.NotificacionHumedad
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar
import com.example.sistemaventilacion.ui.uielements.composables.RangeSliderWithTextFields
import com.example.sistemaventilacion.ui.uielements.composables.TopBar
import kotlin.math.roundToInt

val RangeSaver = Saver<ClosedFloatingPointRange<Float>, List<Float>>(
    save = { listOf(it.start, it.endInclusive) },
    restore = { it[0]..it[1] }
)

@Composable
fun NotificacionHumedadScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                "NotHumScreen",
                "Hub",
                loginRoute = true,
                canGoBack = true

            )
        },
        bottomBar = {
            BottomAppBar(
                navController = navController,
                selected = "NotificacionHumedad",
                onSelected = { navController.navigate(it) })
        }
    ) { paddingValues ->
        NotificacionHumedadStructure(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun NotificacionHumedadStructure(
    navController: NavHostController,
    modifier: Modifier
) {
    val context = LocalContext.current
    var humidityRange by rememberSaveable(stateSaver = RangeSaver) { mutableStateOf(20f..90f) }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text(text = "Umbral de Humedad")
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = "Configure alertas de humedad")
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Humedad de Activación(%)%")
                Spacer(modifier = Modifier.height(8.dp))
                RangeSliderWithTextFields(
                    label = "Humedad de Activación(%)",
                    unit = "%",
                    minGlobal = 20f,
                    maxGlobal = 90f,
                    currentRange = humidityRange,
                    onRangeChange = { humidityRange = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Nota: Rango válido 20-90%")
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        val minHum = humidityRange.start.roundToInt()
                        val maxHum = humidityRange.endInclusive.roundToInt()

                        val humidity = NotificacionHumedad(minHum.toFloat(), maxHum.toFloat())
                        val userId = "ID_DEL_USUARIO"

                        notificacionHumedadRepository(
                            userId = userId,
                            humedad = humidity,
                            onResult = { success, message ->
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        )

                        /*TODO hacer un repositor por guardar el rango de para realizar una notificación al celular.
                            Generar un toast para notificar guardado humedad entre el rango y validar que no se nulo y no crashear el programa*/
                    }

                ) {

                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = "Guardar",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Guardar notificación")
                }
            }
        }
    }
}
