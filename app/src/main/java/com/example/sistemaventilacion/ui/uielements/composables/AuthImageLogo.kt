package com.example.sistemaventilacion.ui.uielements.composables

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.sistemaventilacion.R

@Composable
fun AuthImageLogo() {
    ImageElement(
        R.drawable.logozeusair,
        "LogoZeusAirApp",
        Modifier
            .size(60.dp)

    )
}