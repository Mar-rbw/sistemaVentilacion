package com.example.sistemaventilacion.ui.uielements.composables

import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun TimePicker(onTimeSelected: (String) -> Unit, selectedTime: String) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hour, minute ->
                val formattedTime = String.format(
                    Locale.getDefault(),
                    "%02d:%02d",
                    hour,
                    minute
                )
                onTimeSelected(formattedTime)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true // 24-hour view
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { timePickerDialog.show() }) {
            Text("Seleccionar hora")
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = selectedTime.ifEmpty { "Sin hora" },
            style = MaterialTheme.typography.bodyMedium
        )
    }
}