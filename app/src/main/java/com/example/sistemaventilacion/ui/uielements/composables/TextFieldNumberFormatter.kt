package com.example.sistemaventilacion.ui.uielements.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun TextFieldNumberFormatter(
    value: String,
    onValueChange: (String) -> Unit,
    textLabel: String,
    textInput: String,
    idElement: Int,
    descripcion: String,
    modifierColumn: Modifier = Modifier,
    modifierTextField: Modifier = Modifier,
    modifierLeadingIcon: Modifier = Modifier
) {
    Column(modifierColumn) {
        TextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() } || newValue.isEmpty()) {
                    onValueChange(newValue)
                }
            },
            label = { Text(textLabel) },
            placeholder = { Text(textInput) },
            leadingIcon = {
                ImageElement(
                    idElement,
                    descripcion,
                    modifierLeadingIcon
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            modifier = modifierTextField
        )
    }
}