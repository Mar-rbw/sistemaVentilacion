package com.example.sistemaventilacion.ui.uielements.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.data.remote.firebase.HistoryRepository
import com.example.sistemaventilacion.dataclass.History
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar
import com.example.sistemaventilacion.ui.uielements.composables.TopBar
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBar(
                navController = navController,
                title = "Historial",
                backDestination = "Hub",
                canGoBack = true
            )
        },
        bottomBar = {
            BottomAppBar(
                navController = navController,
                selected = "History",
                onSelected = { navController.navigate(it) }
            )
        }
    ) { paddingValues ->
        HistoryStructure(
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun HistoryStructure(modifier: Modifier = Modifier) {
    val historyRepository = remember { HistoryRepository() }
    val currentUser = FirebaseAuth.getInstance().currentUser
    var historyList by remember { mutableStateOf<List<History>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            historyRepository.getAuditLogs(currentUser.uid).collect { logs ->
                historyList = logs
                loading = false
            }
        } else {
            loading = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (loading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (historyList.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Default.History, contentDescription = null, tint = Color.Gray, modifier = Modifier.padding(16.dp))
                Text("No hay registros de actividad", color = Color.Gray)
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(historyList) { history ->
                    HistoryItemCard(history)
                }
            }
        }
    }
}

@Composable
fun HistoryItemCard(history: History) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = history.action,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = formatDate(history.timestamp),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            
            if (!history.newValue.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Detalle: ${history.newValue}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}
