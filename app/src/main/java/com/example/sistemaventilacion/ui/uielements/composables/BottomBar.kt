package com.example.sistemaventilacion.ui.uielements.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.material.bottomappbar.BottomAppBar

@Composable
fun BottomAppBar(navController: NavHostController) {
    BottomAppBar () {
        Text("Auth", modifier = Modifier.clickable{
            navController.navigate(("Auth"))
        } .padding(6.dp))

        Text("Hub", modifier = Modifier.clickable{
            navController.navigate(("Hub"))
        } .padding(6.dp))

        Text("NotificacionHumedad", modifier = Modifier.clickable{
            navController.navigate(("NotificacionHumedad"))
        } .padding(6.dp))

        Text("NotificacionTemperatura", modifier = Modifier.clickable{
            navController.navigate(("NotificacionTemperatura"))
        } .padding(6.dp))

        Text("ActivacionSistema", modifier = Modifier.clickable{
            navController.navigate(("ActivacionSistema"))
        } .padding(6.dp))

        Text("AgendarActivacion", modifier = Modifier.clickable{
            navController.navigate(("AgendarActivacion"))
        } .padding(6.dp))
    }
}