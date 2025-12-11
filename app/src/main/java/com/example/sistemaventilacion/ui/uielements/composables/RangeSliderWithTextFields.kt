package com.example.sistemaventilacion.ui.uielements.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt


@Composable
fun RangeSliderWithTextFields(
    label: String,
    unit: String,
    minGlobal: Float,
    maxGlobal: Float,
    currentRange: ClosedFloatingPointRange<Float>,
    onRangeChange: (ClosedFloatingPointRange<Float>) -> Unit,
    modifier: Modifier = Modifier
) {
    var minText by rememberSaveable(currentRange.start) {
        mutableStateOf(currentRange.start.roundToInt().toString())
    }
    var maxText by rememberSaveable(currentRange.endInclusive) {
        mutableStateOf(currentRange.endInclusive.roundToInt().toString())
    }

    val updateRangeFromText = {
        val newMin = minText.toFloatOrNull()?.coerceIn(minGlobal, maxGlobal) ?: minGlobal
        val newMax = maxText.toFloatOrNull()?.coerceIn(newMin, maxGlobal) ?: maxGlobal

        val finalMin = minOf(newMin, newMax)
        val finalMax = maxOf(newMin, newMax)

        onRangeChange(finalMin..finalMax)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "$label ($unit)",
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            RangeSliderInputField(
                label = "Mínimo ($unit)",
                value = minText,
                onValueChange = { newValue ->
                    minText = newValue
                    updateRangeFromText()
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            RangeSliderInputField(
                label = "Máximo ($unit)",
                value = maxText,
                onValueChange = { newValue ->
                    maxText = newValue
                    updateRangeFromText()
                }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        RangeSlider(
            value = currentRange,
            onValueChange = { range ->
                onRangeChange(range)

                minText = range.start.roundToInt().toString()
                maxText = range.endInclusive.roundToInt().toString()
            },
            valueRange = minGlobal..maxGlobal,
            steps = (maxGlobal - minGlobal).roundToInt() - 1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("${minGlobal.roundToInt()}$unit")
            Text("${maxGlobal.roundToInt()}$unit")
        }

    }
}
