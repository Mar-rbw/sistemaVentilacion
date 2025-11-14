package com.example.sistemaventilacion.ui.uielements.Auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.sistemaventilacion.data.remote.firebase.IniciarSesion
import com.example.sistemaventilacion.ui.uielements.composables.AuthButton
import com.example.sistemaventilacion.ui.uielements.composables.AuthImageLogo
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar
import com.example.sistemaventilacion.ui.uielements.composables.PasswordTextField
import com.example.sistemaventilacion.ui.uielements.composables.TopBar
import com.example.sistemaventilacion.ui.uielements.composables.UsuarioTextField

@Composable
fun AuthScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopBar(
            navController,
            name = "AuthScreen",
            nameRoute = "Auth",
            canGoBack = false,
            inclusive = false,
        ) },
        bottomBar = { BottomAppBar(
            navController = navController
        ) }
    ) { paddingValues ->
        AuthStructure(navController,modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun AuthStructure(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var textUser by rememberSaveable { mutableStateOf("") }
    var textPassword by rememberSaveable { mutableStateOf("") }
    var isLoading by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            AuthImageLogo()
            Text(
                text = "ZeusApp",
            )
            Text(
                text = "Ingrese sus credenciales para acceder al sistema"
            )
            UsuarioTextField(
                value = textUser,
                onValueChange = { textUser = it },
                textLabel = "Usuario",
                textInput = "Ingrese su usuario"
            )
            PasswordTextField(
                value = textPassword,
                onValueChange = { textPassword = it },
                label = "Password",
                placeholder = "Ingrese su Contraseña"
            )

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            AuthButton(
                onClick = {
                    isLoading = true
                    errorMessage = ""

                    IniciarSesion(
                        usuario = textUser,
                        contrasenia = textPassword
                    ) { exitoso, mensaje ->
                        isLoading = false

                        if (exitoso) {
                            Toast.makeText(
                                context,
                                mensaje,
                                Toast.LENGTH_SHORT
                            ).show()

                            navController.navigate("Hub") {
                                popUpTo("Auth") { inclusive = true }
                            }
                        } else {
                            errorMessage = mensaje
                        }
                    }
                },
                enabled = !isLoading && textUser.isNotEmpty() && textPassword.isNotEmpty(),
                isLoading = isLoading
            )
        }
    }
}