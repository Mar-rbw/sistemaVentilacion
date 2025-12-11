package com.example.sistemaventilacion.ui.uielements.composables

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun DatePicker(onDateSelected: (String) -> Unit, selectedDate: String) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val formattedDate = String.format(
                    Locale.getDefault(),
                    "%02d/%02d/%d",
                    dayOfMonth,
                    month + 1,
                    year
                )
                onDateSelected(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { datePickerDialog.show() }) {
            Text("Seleccionar fecha")
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = selectedDate.ifEmpty { "Sin fecha" },
            style = MaterialTheme.typography.bodyMedium
        )
    }
}