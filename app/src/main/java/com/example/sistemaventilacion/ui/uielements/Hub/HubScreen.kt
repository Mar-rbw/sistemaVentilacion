package com.example.sistemaventilacion.ui.uielements.Hub

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.R
import com.example.sistemaventilacion.dataclass.OptionItem
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar
import com.example.sistemaventilacion.ui.uielements.composables.TopBar
import com.example.sistemaventilacion.ui.uielements.composables.ImageElement
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning


@Composable
fun HubScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopBar(
            navController = navController,
            name = "HubScreen",
            nameRoute = "Auth",
            canGoBack = true,
            inclusive = false
        )},
        bottomBar = {BottomAppBar(
            navController = navController
        )}
    ) { paddingValues ->
        HubStructure(navController, modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun HubStructure(navController: NavHostController, modifier: Modifier) {
    val context = LocalContext.current


    var selectedOption by rememberSaveable { mutableStateOf<OptionItem?>(null) }

    Column(modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)) {

        Header(navController)
        Spacer(modifier = Modifier.height(16.dp))
        SubtitleHub()
        Spacer(modifier = Modifier.height(24.dp))

        ColumnContent(
            navController = navController,
            context = context,
            onOptionClicked = { selectedOption = it } // Al hacer clic en una opción, se abre la tarjeta
        )
    }

    if (selectedOption != null) {
        OptionSettingsCard(
            option = selectedOption!!,
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
        icon = { Icon(Icons.Default.Warning, contentDescription = "Configuración", tint = MaterialTheme.colorScheme.primary) },
        title = { Text("Configurar: ${option.title}") },
        text = {
            Column {
                Text(option.description ?: "Ajuste la configuración de esta opción.")
                Spacer(modifier = Modifier.height(16.dp))

                // Campo de entrada simulado para Modificar/Agregar
                OutlinedTextField(
                    value = inputState,
                    onValueChange = { inputState = it },
                    label = { Text("Valor de Configuración") },
                    placeholder = { Text("Ej: 15°C o 7:00 AM") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botones de acción (Default/Agregar)
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
                    TextButton(onClick = {
                        Toast.makeText(context, "Nueva configuración agregada: $inputState", Toast.LENGTH_SHORT).show()
                    }) {
                        Text("Agregar")
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                // Lógica de Guardar: Simplemente mostramos el Toast y cerramos.
                Toast.makeText(context, "Configuración de ${option.title} guardada: $inputState", Toast.LENGTH_SHORT).show()
                onClose()
            }) {
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

/*
* Función a cargo de estructurar el encabezado de "HubActivity"
*/
@Composable
fun Header(navController: NavHostController){
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HubImageLogo()
        Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
            TitleHub()
        }
        LogOutHub(navController)
    }
}

@Composable
fun HubImageLogo(){
    val logo = painterResource(R.drawable.imagenlogoasset)
    Image(
        painter = logo,
        contentDescription = "Logo App",
        modifier = Modifier.size(48.dp)
    )
}

@Composable
fun HubLogoLogOut(){
    val logo = painterResource(R.drawable.poweroff)
    Image(
        painter = logo,
        contentDescription = "Cerrar Sesión",
        modifier = Modifier.size(24.dp)
    )
}

/*
* Función a cargo de crear el botón de LogOut y su LÓGICA
*/
@Composable
fun LogOutHub(navController: NavHostController){
    val context = LocalContext.current
    Button(
        onClick = {
            // 1. Placeholder para la lógica de Firebase Sign Out
            // FirebaseAuth.getInstance().signOut()

            Toast.makeText(context, "Cerrando sesión...", Toast.LENGTH_SHORT).show()

            // 2. Navegación: Volver a la pantalla de autenticación ("Auth")
            navController.navigate("Auth") {
                // Borra la pila de navegación para que el usuario no pueda volver al Hub
                popUpTo("HubScreen") { inclusive = true }
            }
        },
        modifier = Modifier.padding(start = 8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        HubLogoLogOut()
    }
}

/*Función a cargo de presentar el titulo y subtitulo*/
@Composable
fun TitleHub() {
    // <<< Tipografía restaurada
    Text("Bienvenido", style = MaterialTheme.typography.titleMedium)
    Text("ZeusApp", style = MaterialTheme.typography.headlineSmall)
}

/*
* Función a cargo de presentar el subtitulo
*/
@Composable
fun SubtitleHub() {
    Column {
        // <<< Tipografía restaurada
        Text("¿Qué desea hacer?", style = MaterialTheme.typography.titleSmall)
        Text("Seleccione una opción para configurar el sistema", style = MaterialTheme.typography.bodySmall)
    }
}

// -------------------------------------------------------------------
// CONTENIDO PRINCIPAL Y BOTONES
// -------------------------------------------------------------------

@Composable
fun ColumnContent(
    navController: NavHostController,
    context: Context,
    onOptionClicked: (OptionItem) -> Unit // NUEVO: Handler de click para abrir la Card
){
    // Las opciones ahora solo definen la metadata, el click real lo maneja el padre
    val ventilacionOptions = listOf(
        OptionItem(
            title = "Activación Inmediata",
            description = "Encender/Apagar el sistema de ventilación.",
            icon = null,
            onClick = { navController.navigate("ActivacionSistema") } // Acción final
        ),
        OptionItem(
            title = "Agendar Tarea",
            description = "Programar encendido y apagado automáticos.",
            icon = { ImageElement(R.drawable.poweron, "logoPowerOn") },
            onClick = { navController.navigate("AgendarActivacion") } // Acción final
        )
    )

    val notificacionesOptions = listOf(
        OptionItem("Temp. Mínima", "Notificar si baja de 15°C", null,
            { Toast.makeText(context, "Configurar Temp", Toast.LENGTH_SHORT).show() }), // Acción final
    )

    ButtonExpansibleWithOptions(
        title = "Control de Ventilación",
        subtitle = "Configurar modos y horarios de operación.",
        icon = R.drawable.imagenlogoasset,
        options = ventilacionOptions,
        onOptionClicked = onOptionClicked // Se pasa el handler a la función expansible
    )
    Spacer(modifier = Modifier.height(16.dp))

    ButtonExpansibleWithOptions(
        title = "Notificaciones y Alarmas",
        subtitle = "Ajustar umbrales de temperatura y humedad.",
        icon = R.drawable.imagenlogoasset,
        options = notificacionesOptions,
        onOptionClicked = onOptionClicked // Se pasa el handler
    )
}

// -------------------------------------------------------------------
// COMPOSABLES REUTILIZABLES (Estilos restaurados)
// -------------------------------------------------------------------

@Composable
fun ButtonTitle(title: String){
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium
    )
}

@Composable
fun ButtonSubtitle(subtitle: String){
    Text(
        text = subtitle,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun ButtonIcon(@DrawableRes iconRes: Int) {
    val icon = painterResource(iconRes)
    Image(
        painter = icon,
        contentDescription = null,
        modifier = Modifier.size(24.dp)
    )
}

@Composable
fun ButtonElement(){
    val element = painterResource(R.drawable.displaybutton)
    Image(
        painter = element,
        contentDescription = "Expandir",
        modifier = Modifier.size(24.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonExpansibleWithOptions(
    title: String,
    subtitle: String,
    icon: Int,
    options: List<OptionItem>,
    modifier: Modifier = Modifier,
    onOptionClicked: (OptionItem) -> Unit // NUEVA FIRMA: El click solo notifica
){
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
                    OptionButton(optionItem = option, onClick = {
                        onOptionClicked(option) // <<< CAMBIO: Notifica al padre en lugar de ejecutar la acción final
                        expandir = false // Cierra el menú al seleccionar
                    })
                    // Agrega el divisor solo si no es el último elemento
                    if (index < options.lastIndex) {
                        Divider(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f))
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
                if (optionItem.description != null) {
                    Text(
                        text = optionItem.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (optionItem.icon != null) {
                Spacer(modifier = Modifier.width(8.dp))
                optionItem.icon.invoke()
            }
        }
    }
}