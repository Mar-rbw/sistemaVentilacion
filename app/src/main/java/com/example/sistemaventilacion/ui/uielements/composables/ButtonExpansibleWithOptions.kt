package com.example.sistemaventilacion.ui.uielements.composables

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sistemaventilacion.dataclass.OptionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonExpansibleWithOptions(
    title: String,
    subtitle: String,
    @DrawableRes icon: Int,
    options: List<OptionItem>,
    modifier: Modifier = Modifier,
    onOptionClicked: (OptionItem) -> Unit
) {
    var expandir by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxWidth()) {
        Card(
            onClick = { expandir = !expandir },
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.8f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ButtonIcon(icon)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(verticalArrangement = Arrangement.Center) {
                        Text(text = title, style = MaterialTheme.typography.titleMedium)
                        Text(text = subtitle, style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Box(contentAlignment = Alignment.Center) {
                    ButtonElement()
                }
            }
        }

        AnimatedVisibility(
            visible = expandir,
            enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
            exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                options.forEachIndexed { index, option ->
                    OptionButton(
                        optionItem = option,
                        onClick = {
                            option.onClick()

                            expandir = false
                        }
                    )
                    if (index < options.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier,
                            thickness = DividerDefaults.Thickness,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f)
                        )
                    }
                }
            }
        }
    }
}
