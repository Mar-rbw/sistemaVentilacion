package com.example.sistemaventilacion.ui.uielements.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.sistemaventilacion.R

@Composable
fun UsuarioTextField(
    value: String,
    onValueChange: (String) -> Unit,
    textLabel: String,
    textInput: String,
) {
    Column() {
        TextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.all { it.isLetterOrDigit() } || newValue.isEmpty()) {
                    onValueChange(newValue)
                }
            },
            label = { Text(textLabel) },
            placeholder = { Text(textInput) },
            leadingIcon = {
                ImageElement(
                    R.drawable.user,
                    "UserIcon",
                    Modifier.padding(end = 4.dp)
                )
            },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            ),
            singleLine = true
        )
    }
}