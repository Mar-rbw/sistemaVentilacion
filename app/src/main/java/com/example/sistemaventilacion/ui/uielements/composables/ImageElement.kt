package com.example.sistemaventilacion.ui.uielements.composables

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun ImageElement(
    @DrawableRes elementId: Int,
    description: String,
    modifier: Modifier = Modifier
){
    val image = painterResource(id = elementId)
    Image(
        painter = image,
        contentDescription = description,
        modifier = modifier
    )
}