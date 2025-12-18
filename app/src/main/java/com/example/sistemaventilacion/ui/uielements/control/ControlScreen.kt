package com.example.sistemaventilacion.ui.uielements.control

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.data.remote.firebase.HistoryRepository
import com.example.sistemaventilacion.data.remote.firebase.IsManualOnRepository
import com.example.sistemaventilacion.dataclass.ActionType
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar
import com.example.sistemaventilacion.ui.uielements.composables.TopBar
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ControlScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                title = "Control Manual",
                backDestination = "Hub",
                canGoBack = true
            )
        },
        bottomBar = {
            BottomAppBar(
                navController = navController,
                selected = "Control",
                onSelected = { navController.navigate(it) }
            )
        }
    ) { paddingValues ->
        ControlStructure(
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun ControlStructure(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val repository = remember { IsManualOnRepository() }
    val historyRepository = remember { HistoryRepository() }
    var isManualOn by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(true) }

    // Load initial state
    LaunchedEffect(Unit) {
        repository.getIsManualOn(
            onResult = {
                isManualOn = it
                loading = false
            },
            onError = {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                loading = false
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.PowerSettingsNew,
                    contentDescription = "Power",
                    modifier = Modifier.size(64.dp),
                    tint = if (isManualOn) Color(0xFF43A047) else Color.Gray
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Sistema de Ventilación",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = if (isManualOn) "ENCENDIDO MANUALMENTE" else "APAGADO / AUTOMÁTICO",
                    color = if (isManualOn) Color(0xFF43A047) else Color.Red,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (loading) {
                    CircularProgressIndicator()
                } else {
                    Switch(
                        checked = isManualOn,
                        onCheckedChange = { newState ->
                            // Optimistic update
                            isManualOn = newState
                            
                            repository.setIsManualOn(
                                value = newState,
                                onSuccess = {
                                    val currentUser = FirebaseAuth.getInstance().currentUser
                                    historyRepository.postAuditLog(
                                        userId = currentUser?.uid ?: "",
                                        userName = currentUser?.email ?: "Usuario",
                                        action = if (newState) "Encendido Manual Activado" else "Encendido Manual Desactivado",
                                        actionType = ActionType.TASK_MANUAL_CONTROL,
                                        newValue = if (newState) "ON" else "OFF"
                                    )
                                    Toast.makeText(context, "Estado actualizado", Toast.LENGTH_SHORT).show()
                                },
                                onError = {
                                    isManualOn = !newState // Revert
                                    Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
                                }
                            )
                        },
                        modifier = Modifier.scale(1.5f),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(0xFF43A047),
                            checkedTrackColor = Color(0xFFA5D6A7),
                            uncheckedThumbColor = Color.Red,
                            uncheckedTrackColor = Color(0xFFEF9A9A)
                        )
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "Nota: El control manual sobrescribe la automatización por temperatura.",
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

// Extension helper for scaling the switch if needed, or just remove modifier if not
fun Modifier.scale(scale: Float) = this.then(Modifier) 
