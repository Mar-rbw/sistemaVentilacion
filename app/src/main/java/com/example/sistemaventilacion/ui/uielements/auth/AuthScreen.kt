package com.example.sistemaventilacion.ui.uielements.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.ui.uielements.composables.AuthImageLogo
import com.example.sistemaventilacion.ui.uielements.composables.BottomAppBar
import com.example.sistemaventilacion.ui.uielements.composables.TopBar

@Composable
fun AuthScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBar(
                navController,
                "AuthScreen",
                "Auth",
                loginRoute = false,
                canGoBack = false,
            )
        },
        bottomBar = {
            BottomAppBar(
                navController = navController,
                selected = "Auth",
                onSelected = { navController.navigate(it) }
            )
        }
    ) { paddingValues ->
        AuthStructure(
            navController,
            modifier = Modifier.padding(paddingValues)
            )
    }
}

@Composable
fun AuthStructure(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(15.dp)
            .background(color = Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

    )
    {
        AuthImageLogo()
        Spacer(modifier = Modifier
            .padding(5.dp)
        )
        Text(
            text = "ZeusApp",
            fontSize = 50.sp,
            fontWeight = FontWeight.W600,
            textAlign = TextAlign.Center,
            color = Color(0xFF1976D2)
        )
        Spacer(modifier = Modifier.padding(5.dp))
        Text(
            text = "Ingrese sus credenciales para acceder al sistema",
            fontSize = 25.sp,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            onClick = {
                navController.navigate(
                        "AuthRegister"
                    )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2),
                contentColor = Color.White
            )
        )
        {
            Text("Registrarse",
                fontSize = 25.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center,)
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            onClick = {
                navController.navigate(
                    "AuthLogin")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2),
                contentColor = Color.White
            )
        )
        {
            Text("Iniciar Sesión",
                fontSize = 25.sp,
                fontWeight = FontWeight.W400,
                textAlign = TextAlign.Center,)
        }
    }
}