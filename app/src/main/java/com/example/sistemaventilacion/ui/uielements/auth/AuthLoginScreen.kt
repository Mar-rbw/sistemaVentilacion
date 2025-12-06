package com.example.sistemaventilacion.ui.uielements.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar
import com.example.sistemaventilacion.ui.uielements.composables.TopBar
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AuthLoginScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopBar(
            navController,
            "AuthLoginScreen",
            "Auth",
            loginRoute = true,
            canGoBack = true,
        ) },
        bottomBar = { BottomAppBar(
            navController = navController,
            selected = "AuthLogin",
            onSelected = { navController.navigate(it) }
        ) }
    ) { paddingValues ->
        AuthLoginStructure(navController)
    }
}

@Composable
fun AuthLoginStructure(
    navController: NavController
) {
    val auth = FirebaseAuth.getInstance()

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var loading by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                loading = true
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        loading = false
                        if (task.isSuccessful) {
                            Toast.makeText(
                                navController.context,
                                "Login exitoso!",
                                Toast.LENGTH_SHORT
                            ).show()

                            navController.navigate("AuthLogin") {
                                popUpTo("Hub") { inclusive = true }
                            }

                        } else {
                            Toast.makeText(
                                navController.context,
                                "Login fallido: ${task.exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !loading
        ) {
            Text(if (loading) "Ingresando..." else "Ingresar")
        }
    }
}