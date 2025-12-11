package com.example.sistemaventilacion.ui.uielements.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sistemaventilacion.R
import com.example.sistemaventilacion.utils.SessionManager

/*https://medium.com/@banmarkovic/jetpack-compose-clear-back-stack-popbackstack-inclusive-explained-14ee73a29df5*/


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavController,
    title: String,
    backDestination: String? = null,
    loginRoute: Boolean = true,
    canGoBack: Boolean = true,
    inclusivePop: Boolean = false,
) {
    val context = LocalContext.current

    val debouncedGoBack = RememberDebouncedAction {
        backDestination?.let {
            navController.popBackStack(it, inclusivePop)
        } ?: navController.popBackStack()
    }

    val debouncedLogout = RememberDebouncedAction {
        SessionManager(context).clearSession()
        val targetLoginRoute = if (loginRoute) "AuthLogin" else "Auth"

        navController.navigate(targetLoginRoute) {
            popUpTo(0) { inclusive = true }
        }
    }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.W800,
                textAlign = TextAlign.Center,
            )
        },

        navigationIcon = {
            if (canGoBack) {
                IconButton(
                    onClick = { debouncedGoBack() }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Regresar",
                        tint = Color.White,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            }
        },

        actions = {
            if(canGoBack){
                IconButton(onClick = { debouncedLogout() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = "Cerrar sesión",
                        tint = Color.Red
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF1976D2),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}
