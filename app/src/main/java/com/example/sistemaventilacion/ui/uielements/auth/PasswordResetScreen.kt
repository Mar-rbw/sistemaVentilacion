package com.example.sistemaventilacion.ui.uielements.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.data.remote.modelView.ResetPasswordViewModel
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar
import com.example.sistemaventilacion.ui.uielements.composables.TopBar

@Composable
fun PasswordResetScreen(navController: NavHostController){

    Scaffold(
        topBar = {
            TopBar(
                navController,
                "PasswordResetScreen",
                "Auth",
                loginRoute = true,
                canGoBack = true,
            )
        },
        bottomBar = {
            BottomAppBar(
                navController = navController,
                selected = "AuthLogin",
                onSelected = { navController.navigate(it) }
            )
        }
    ){ paddingValues ->
        PasswordResetStructure(navController, modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun PasswordResetStructure(navController: NavHostController,
                           modifier: Modifier,
                           viewModel: ResetPasswordViewModel = viewModel()){

    val resetState by viewModel.resetState.collectAsState()
    var email by rememberSaveable { mutableStateOf("") }

    if (resetState.isSent) {
        LaunchedEffect(Unit){
            kotlinx.coroutines.delay(5000)
            navController.navigate("AuthLogin"){
                popUpTo(navController.graph.startDestinationId) {inclusive = false}
            }
            viewModel.resetState()
        }
    }

    Column(
        modifier = modifier
            .padding(24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(Modifier.height(80.dp))

        Text(
            "Recuperación de Contraseña",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            "Ingrese su correo electrónico para recibir el enlace de restablecimiento.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(Modifier.height(24.dp))

        TextField(
            value = email,
            onValueChange = {
                email = it
                if (resetState.error != null) { viewModel.resetState() }
            },
            label = { Text("Correo Electrónico") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            placeholder = { Text("ejemplo@correo.com") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))

        Button(
            onClick ={ viewModel.sendPasswordReset(email) },
            enabled = !resetState.isLoading && email.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (resetState.isLoading) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.height(20.dp))
                    Spacer(Modifier.padding(4.dp))
                    Text("Enviando...")
                }
            } else {
                Text("Enviar Enlace de Recuperación")
            }
        }

        Spacer(Modifier.height(16.dp))

        if (resetState.isSent) {
            Text(
                "Correo enviado con éxito. ",
                color = Color.Green,
                textAlign = TextAlign.Center
            )
        }

        resetState.error?.let {error ->
            Text(
                "Error: $error",
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}