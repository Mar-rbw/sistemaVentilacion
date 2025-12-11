package com.example.sistemaventilacion.ui.uielements.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun ButtonIcon(@DrawableRes iconRes: Int) {
    Image(
        painter = painterResource(iconRes),
        contentDescription = null,
        modifier = Modifier.size(24.dp)
    )
}