package com.example.sistemaventilacion.ui.uielements.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar
import com.example.sistemaventilacion.ui.uielements.composables.TopBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException


@Composable
fun AuthRegisterScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBar(
                navController,
                "AuthRegisterScreen",
                "Auth",
                loginRoute = true,
                canGoBack = true,
            )
        },
        bottomBar = {
            BottomAppBar(
                navController = navController,
                selected = "AuthRegister",
                onSelected = { navController.navigate(it) }
            )
        }
    ) { paddingValues ->
        AuthRegisterStructure(navController, modifier = Modifier.padding(paddingValues))
    }
}

@Composable
fun AuthRegisterStructure(navController: NavController, modifier: Modifier) {
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current


    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var loading by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ZeusApp",
            fontSize = 35.sp,
            fontWeight = FontWeight.W600,
            textAlign = TextAlign.Center,
            color = Color(0xFF1976D2)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(
            text = "Cree un perfil para acceder al sistema",
            fontSize = 25.sp,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        Spacer(modifier = Modifier.padding(10.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email",
                    tint = Color(0xFF1976D2)
                )
            },
            placeholder = { Text("ejemplo@correo.com") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Contraseña",
                    tint = Color(0xFF1976D2)
                )
            },
            placeholder = { Text("Ej: 12345") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    Toast.makeText(
                        context,
                        "Correo y contraseña no pueden estar vacíos",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    loading = true
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            loading = false
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Registro exitoso",
                                    Toast.LENGTH_LONG
                                ).show()
                                navController.navigate("AuthLogin") {
                                    popUpTo("AuthRegister") { inclusive = true }
                                }
                            } else {
                                val errorMessage = when (task.exception) {
                                    is FirebaseAuthUserCollisionException -> {
                                        "Error: El correo electrónico ya está registrado."
                                    }

                                    else -> {
                                        "Error: ${task.exception?.message}"
                                    }
                                }
                                Toast.makeText(
                                    context,
                                    errorMessage,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2),
                contentColor = Color.White
            ),
            enabled = !loading

        ) {
            Text(
                text = if (loading) "Creando..." else "Registrarse",
                fontSize = 25.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center,
            )
        }
    }
}