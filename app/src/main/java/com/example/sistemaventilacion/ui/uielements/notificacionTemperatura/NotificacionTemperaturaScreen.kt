package com.example.sistemaventilacion.ui.uielements.notificacionHumedad

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.R
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar
import com.example.sistemaventilacion.ui.uielements.composables.ImageElement
import com.example.sistemaventilacion.ui.uielements.composables.TopBar
import java.util.Locale

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
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun NotificacionTemperaturaStructure(modifier: Modifier) {
    val context = LocalContext.current
    var temperaturaRange by rememberSaveable { mutableStateOf(15f..35f) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TitleNofTem()
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                FormTitleNofTem()
                Spacer(modifier = Modifier.height(16.dp))
                TemperaturaRangeSlider(
                    temperaturaRange = temperaturaRange,
                    onRangeChange = { temperaturaRange = it }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val message = String.format(
                    Locale.getDefault(),
                    "Configuración guardada: %.0f°C - %.0f°C",
                    temperaturaRange.start,
                    temperaturaRange.endInclusive
                )
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Guardar Configuración")
        }
    }
}

@Composable
fun TitleNofTem() {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(
            text = "Umbral de Temperatura",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = "Configuración de notificaciones",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun FormTitleNofTem() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        ImageElement(
            R.drawable.humidity,
            "Icono de temperatura",
            Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = "Notificación de Temperatura",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Defina el rango de temperatura aceptable (°C)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun TemperaturaRangeSlider(
    temperaturaRange: ClosedFloatingPointRange<Float>,
    onRangeChange: (ClosedFloatingPointRange<Float>) -> Unit
) {
    val minRange = 0f
    val maxRange = 50f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = String.format(
                    Locale.getDefault(),
                    "Mínimo: %.0f°C",
                    temperaturaRange.start
                ),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = String.format(
                    Locale.getDefault(),
                    "Máximo: %.0f°C",
                    temperaturaRange.endInclusive
                ),
                style = MaterialTheme.typography.titleMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        RangeSlider(
            value = temperaturaRange,
            onValueChange = { range ->
                onRangeChange(range)
            },
            valueRange = minRange..maxRange,
            steps = (maxRange.toInt() - minRange.toInt()) - 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = String.format(Locale.getDefault(), "%.0f°C", minRange),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = String.format(Locale.getDefault(), "%.0f°C", maxRange),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}