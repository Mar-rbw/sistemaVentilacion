package com.example.sistemaventilacion.ui.uielements.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.sistemaventilacion.R

/*https://medium.com/@banmarkovic/jetpack-compose-clear-back-stack-popbackstack-inclusive-explained-14ee73a29df5*/


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavHostController,
    name: String,
    nameRoute: String,
    canGoBack: Boolean = true,
    inclusive: Boolean
) {
    val debouncedPopBack = RememberDebouncedAction {
        navController.popBackStack(nameRoute, inclusive)
    }
    TopAppBar(
        title = {
            Text(text = name)
        },
        navigationIcon = {
            if (canGoBack) {
                ImageElement(
                    R.drawable.backbutton,
                    "LogoBackButton",
                    modifier = Modifier
                        .clickable {
                            debouncedPopBack
                        }
                        .padding(8.dp)
                )
            }
        }
    )
}