package com.example.sistemaventilacion.ui.uielements.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sistemaventilacion.ui.uielements.hub.ButtonElement

@Composable
fun SystemPowerOff(){
    var apagar by rememberSaveable { mutableStateOf(false) }
    Box{
        Button(onClick = {apagar = !apagar}) {
            Row {
                ButtonElement()
                Spacer(modifier = Modifier.height(8.dp))
                Text("Apagar Sistema")
            }
        }
    }
}