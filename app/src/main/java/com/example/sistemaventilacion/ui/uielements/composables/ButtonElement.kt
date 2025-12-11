package com.example.sistemaventilacion.ui.uielements.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.sistemaventilacion.R

@Composable
fun ButtonElement() {
    Image(
        painter = painterResource(R.drawable.displaybutton),
        contentDescription = "Expandir",
        modifier = Modifier.size(24.dp)
    )
}