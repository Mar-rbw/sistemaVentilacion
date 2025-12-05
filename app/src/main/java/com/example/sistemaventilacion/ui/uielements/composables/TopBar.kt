package com.example.sistemaventilacion.ui.uielements.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.R

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
    val debouncedGoBack = RememberDebouncedAction {
        backDestination?.let {
            navController.popBackStack(it, inclusivePop)
        } ?: navController.popBackStack()
    }

    val debouncedLogout = RememberDebouncedAction {
        navController.navigate(loginRoute) {
            popUpTo(0) { inclusive = true }
        }
    }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = Color.White
            )
        },

        navigationIcon = {
            if (canGoBack) {
                ImageElement(
                    R.drawable.backbutton,
                    "LogoBackButton",
                    modifier = Modifier
                        .padding(15.dp)
                        .clickable {
                            debouncedGoBack()
                        }
                )
            }
        },

        actions = {
            IconButton(onClick = { debouncedLogout() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Cerrar sesión",
                    tint = Color.White
                )
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
