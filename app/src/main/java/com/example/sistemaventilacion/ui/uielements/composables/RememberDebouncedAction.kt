package com.example.sistemaventilacion.ui.uielements.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

/*
* Estos parametros utilizan como medida milisegundos
* https://developer.android.com/guide/navigation/backstack?hl=es-419
*https://developer.android.com/reference/java/lang/System
* */

@Composable
fun RememberDebouncedAction(
    debounceTime: Long = 300L,
    action: () -> Unit ): () -> Unit {
    val lastClickTime = rememberSaveable { mutableStateOf(0L) }

    return {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastClickTime.value > debounceTime) {
            lastClickTime.value = currentTime
            action()
        }
    }
}