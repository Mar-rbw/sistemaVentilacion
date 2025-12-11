package com.example.sistemaventilacion.ui.uielements.composables

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Monitor
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.sistemaventilacion.R
import com.example.sistemaventilacion.dataclass.IconSource
import com.example.sistemaventilacion.dataclass.NavChip


@Composable
fun BottomAppBar(
    navController: NavController,
    selected: String,
    onSelected: (String) -> Unit
) {
    val items = listOf(
        NavChip("Hub", "Hub", IconSource.Vector(Icons.Default.Home)),
        NavChip("Monitoreo", "Monitor", IconSource.Vector(Icons.Default.Monitor)),
        NavChip("Control", "Control", IconSource.Vector(Icons.Default.Bolt)),
        NavChip(
            "NotificacionHumedad",
            "Notificacion de Humedad",
            IconSource.Resource(R.drawable.outline_humidity_percentage_24)
        ),
        NavChip(
            "NotificacionTemperatura",
            "Notificacion de Temperatura",
            IconSource.Vector(Icons.Default.DeviceThermostat)
        ),
        NavChip("ActivacionSistema", "Activacion del Sistema", IconSource.Vector(Icons.Default.PowerSettingsNew)),
        NavChip("AgendarActivacion", "Agendar Activacion", IconSource.Vector(Icons.Default.CalendarMonth)),
        NavChip("Historial", "Historial", IconSource.Vector(Icons.Default.History))
    )

    @Composable
    fun NavChipIcon(iconSource: IconSource) {
        val tint = MaterialTheme.colorScheme.primary

        when (iconSource) {
            is IconSource.Vector -> Icon(
                imageVector = iconSource.imageVector,
                contentDescription = null,
                tint = tint
            )

            is IconSource.Resource -> Icon(
                painter = painterResource(id = iconSource.resId),
                contentDescription = null,
                tint = tint
            )
        }
    }

    BottomAppBar(
        tonalElevation = 4.dp,
        containerColor = Color(0xFF1976D2),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                AssistChip(
                    onClick = {
                        if (item.route != selected) {
                            onSelected(item.route)
                            navController.navigate(item.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    label = { Text(item.label) },
                    leadingIcon = { NavChipIcon(item.icon) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor =
                            if (selected == item.route)
                                Color(0xFFF76B1C)
                            else
                                MaterialTheme.colorScheme.surface,
                        labelColor =
                            if (selected == item.route)
                                Color(0xFFFAD961)
                            else
                                MaterialTheme.colorScheme.onSurface,
                        leadingIconContentColor =
                            if (selected == item.route)
                                Color(0xFFFAD961)
                            else
                                MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}