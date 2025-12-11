package com.example.sistemaventilacion.ui.uielements.composables

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun RangeSliderInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
){
    TextField(
        value = value,
        onValueChange = {
            newValue ->
            if (newValue.all {it.isDigit()}){
                onValueChange(newValue)
            } else if (newValue.isEmpty()){
                onValueChange(newValue)
            }
        },
        label = {Text(label)},
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = Modifier.width(120.dp)
    )
}