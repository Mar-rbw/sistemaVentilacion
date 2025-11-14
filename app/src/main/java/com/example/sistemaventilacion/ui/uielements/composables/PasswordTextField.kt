package com.example.sistemaventilacion.ui.uielements.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.sistemaventilacion.R

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
) {
    var passwordVisible by rememberSaveable {mutableStateOf((false))}
    Column() {
        TextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.all { it.isLetterOrDigit() } || newValue.isEmpty()) {
                    onValueChange(newValue)
                }
            },
            singleLine = true,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            visualTransformation =
                if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                val  image = if (passwordVisible) R.drawable.visibility else R.drawable.offvisibility
                val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
                IconButton(onClick = {passwordVisible = !passwordVisible}){
                    ImageElement(image, description)
                }
            }
        )
    }
}