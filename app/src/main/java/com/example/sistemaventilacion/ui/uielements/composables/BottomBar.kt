package com.example.sistemaventilacion.ui.uielements.composables

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        NavChip("Monitor", "Monitor", IconSource.Vector(Icons.Default.Bolt)),
        NavChip("Control", "Control", IconSource.Vector(Icons.Default.Bolt)),
        NavChip(
            "Notificación Humedad",
            "NotificacionHumedad",
            IconSource.Resource(R.drawable.outline_humidity_percentage_24)
        ),
        NavChip(
            "Notificación Temp",
            "NotificacionTemperatura",
            IconSource.Vector(Icons.Default.DeviceThermostat)
        ),
        NavChip("Activación", "ActivacionSistema", IconSource.Vector(Icons.Default.Bolt)),
        NavChip("Agendar", "AgendarActivacion", IconSource.Vector(Icons.Default.CalendarMonth)),
        NavChip("Historia", "Historia", IconSource.Vector(Icons.Default.Bolt))
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
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 8.dp),
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
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                            else
                                MaterialTheme.colorScheme.surface,
                        labelColor =
                            if (selected == item.route)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurface,
                        leadingIconContentColor =
                            if (selected == item.route)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}
