package com.example.sistemaventilacion.ui.uielements.hub

import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.R
import com.example.sistemaventilacion.dataclass.OptionItem
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar
import com.example.sistemaventilacion.ui.uielements.composables.ImageElement
import com.example.sistemaventilacion.ui.uielements.composables.TopBar
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HubScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBar(
                navController,
                "HubScreen",
                "Auth",
                loginRoute = true,
                canGoBack = false,
                inclusivePop = true
            )
        },
        bottomBar = {
            BottomAppBar(
                navController = navController,
                selected = "Hub",
                onSelected = { navController.navigate(it) })
        }
    ) { paddingValues ->
        HubStructure(navController, modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun HubStructure(navController: NavHostController, modifier: Modifier) {
    val context = LocalContext.current
    var selectedOption by rememberSaveable { mutableStateOf<OptionItem?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Header(navController)
        Spacer(modifier = Modifier.height(16.dp))
        SubtitleHub()
        Spacer(modifier = Modifier.height(24.dp))

        ColumnContent(
            navController = navController,
            context = context,
            onOptionClicked = { selectedOption = it }
        )
    }

    selectedOption?.let { option ->
        OptionSettingsCard(
            option = option,
            onClose = { selectedOption = null }
        )
    }
}

@Composable
fun OptionSettingsCard(option: OptionItem, onClose: () -> Unit) {
    val context = LocalContext.current
    var inputState by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onClose,
        icon = {
            Icon(
                Icons.Default.Warning,
                contentDescription = "Configuración",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        title = { Text("Configurar: ${option.title}") },
        text = {
            Column {
                Text(option.description ?: "Ajuste la configuración de esta opción.")
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = inputState,
                    onValueChange = { inputState = it },
                    label = { Text("Valor de Configuración") },
                    placeholder = { Text("Ej: 15°C o 7:00 AM") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = {
                        inputState = "Default"
                        Toast.makeText(context, "Valor por defecto cargado", Toast.LENGTH_SHORT).show()
                    }) {
                        Text("Default")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(
                        onClick = {
                            if (inputState.isNotBlank()) {
                                Toast.makeText(
                                    context,
                                    "Nueva configuración agregada: $inputState",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        enabled = inputState.isNotBlank()
                    ) {
                        Text("Agregar")
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    Toast.makeText(
                        context,
                        "Configuración de ${option.title} guardada: $inputState",
                        Toast.LENGTH_SHORT
                    ).show()
                    onClose()
                },
                enabled = inputState.isNotBlank()
            ) {
                Text("Guardar y Cerrar")
            }
        },
        dismissButton = {
            TextButton(onClick = onClose) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun Header(navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HubImageLogo()
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            TitleHub()
        }
        LogOutHub(navController)
    }
}

@Composable
fun HubImageLogo() {
    Image(
        painter = painterResource(R.drawable.logozeusair),
        contentDescription = "Logo App",
        modifier = Modifier.size(48.dp)
    )
}

@Composable
fun HubLogoLogOut() {
    Image(
        painter = painterResource(R.drawable.poweroff),
        contentDescription = "Cerrar Sesión",
        modifier = Modifier.size(24.dp)
    )
}

@Composable
fun LogOutHub(navController: NavHostController) {
    val context = LocalContext.current

    Button(
        onClick = {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show()
            navController.navigate("Auth") {
                popUpTo("HubScreen") { inclusive = true }
            }
        },
        modifier = Modifier.padding(start = 8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        HubLogoLogOut()
    }
}

@Composable
fun TitleHub() {
    Text("Bienvenido", style = MaterialTheme.typography.titleMedium)
    Text("ZeusApp", style = MaterialTheme.typography.headlineSmall)
}

@Composable
fun SubtitleHub() {
    Column {
        Text("¿Qué desea hacer?", style = MaterialTheme.typography.titleSmall)
        Text(
            "Seleccione una opción para configurar el sistema",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun ColumnContent(
    navController: NavHostController,
    context: Context,
    onOptionClicked: (OptionItem) -> Unit
) {
    val ventilacionOptions = remember {
        listOf(
            OptionItem(
                title = "Activación Inmediata",
                description = "Encender/Apagar el sistema de ventilación.",
                icon = null,
                onClick = { navController.navigate("ControlScreen") }
            ),
            OptionItem(
                title = "Agendar Tarea",
                description = "Programar encendido y apagado automáticos.",
                icon = { ImageElement(R.drawable.poweron, "logoPowerOn") },
                onClick = { navController.navigate("AgendarActivacion") }
            )
        )
    }

    val notificacionesOptions = remember {
        listOf(
            OptionItem(
                "Temp. Mínima",
                "Notificar si baja de 15°C",
                null
            ) {
                Toast.makeText(context, "Configurar Temp", Toast.LENGTH_SHORT).show()
            }
        )
    }

    ButtonExpansibleWithOptions(
        title = "Control de Ventilación",
        subtitle = "Configurar modos y horarios de operación.",
        icon = R.drawable.logozeusair,
        options = ventilacionOptions,
        onOptionClicked = onOptionClicked
    )

    Spacer(modifier = Modifier.height(16.dp))

    ButtonExpansibleWithOptions(
        title = "Notificaciones y Alarmas",
        subtitle = "Ajustar umbrales de temperatura y humedad.",
        icon = R.drawable.logozeusair,
        options = notificacionesOptions,
        onOptionClicked = onOptionClicked
    )
}

@Composable
fun ButtonTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun ButtonSubtitle(subtitle: String) {
    Text(
        text = subtitle,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun ButtonIcon(@DrawableRes iconRes: Int) {
    Image(
        painter = painterResource(iconRes),
        contentDescription = null,
        modifier = Modifier.size(24.dp)
    )
}

@Composable
fun ButtonElement() {
    Image(
        painter = painterResource(R.drawable.displaybutton),
        contentDescription = "Expandir",
        modifier = Modifier.size(24.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonExpansibleWithOptions(
    title: String,
    subtitle: String,
    @DrawableRes icon: Int,
    options: List<OptionItem>,
    modifier: Modifier = Modifier,
    onOptionClicked: (OptionItem) -> Unit
) {
    var expandir by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Card(
            onClick = { expandir = !expandir },
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ButtonIcon(icon)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(verticalArrangement = Arrangement.Center) {
                        ButtonTitle(title)
                        ButtonSubtitle(subtitle)
                    }
                }

                Box(contentAlignment = Alignment.Center) {
                    ButtonElement()
                }
            }
        }

        AnimatedVisibility(
            visible = expandir,
            enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
            exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                options.forEachIndexed { index, option ->
                    OptionButton(
                        optionItem = option,
                        onClick = {
                            onOptionClicked(option)
                            expandir = false
                        }
                    )
                    if (index < options.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier,
                            thickness = DividerDefaults.Thickness,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OptionButton(
    optionItem: OptionItem,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = optionItem.title,
                    style = MaterialTheme.typography.bodyLarge
                )
                optionItem.description?.let { desc ->
                    Text(
                        text = desc,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            optionItem.icon?.let { iconComposable ->
                Spacer(modifier = Modifier.width(8.dp))
                iconComposable()
            }
        }
    }
}