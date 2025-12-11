package com.example.sistemaventilacion.ui.uielements.notificacionTemperatura

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
import com.example.sistemaventilacion.data.remote.firebase.NotificacionTemperaturaRepository
import com.example.sistemaventilacion.dataclass.NotificacionTemperatura
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar
import com.example.sistemaventilacion.ui.uielements.composables.RangeSliderWithTextFields
import com.example.sistemaventilacion.ui.uielements.composables.TopBar
import kotlin.math.roundToInt

val RangeSaver = Saver<ClosedFloatingPointRange<Float>, List<Float>>(
    save = { listOf(it.start, it.endInclusive) },
    restore = { it[0]..it[1] }
)


@Composable
fun NotificacionTemperaturaScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                "NotTemScreen",
                "Hub",
                loginRoute = true,
                canGoBack = true
            )
        },
        bottomBar = {
            BottomAppBar(
                navController = navController,
                selected = "NotificacionTemperatura",
                onSelected = { navController.navigate(it) }
            )
        }
    ) { paddingValues ->
        NotificacionTemperaturaStructure(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun NotificacionTemperaturaStructure(
    navController: NavHostController,
    modifier: Modifier
) {
    val context = LocalContext.current
    var temperaturaRange by rememberSaveable(stateSaver = RangeSaver) { mutableStateOf(0f..50f) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Text("Umbral de Temperatura")
        Spacer(modifier = Modifier.height(12.dp))
        Text("Configure alertas de temperatura")

        Spacer(modifier = Modifier.height(32.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Notificación de Temperatura")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Defina el umbral para recibir alertas")
                Spacer(modifier = Modifier.height(8.dp))
                RangeSliderWithTextFields(
                    label = "Temperatura de activación",
                    unit = "°C",
                    minGlobal = 0f,
                    maxGlobal = 50f,
                    currentRange = temperaturaRange,
                    onRangeChange = { temperaturaRange = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Nota: Rango válido 0 - 50 °C")
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val minTem = temperaturaRange.start.roundToInt()
                        val maxTem = temperaturaRange.endInclusive.roundToInt()

                        val temperature = NotificacionTemperatura(
                            MIN_TEM = minTem.toFloat(),
                            MAX_TEM = maxTem.toFloat()
                        )
                        val userId = "ID_DEL_USUARIO"

                        NotificacionTemperaturaRepository(
                            userId = userId,
                            temperatura = temperature,
                            onResult = { success, message ->
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        )
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
