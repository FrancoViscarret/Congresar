package com.example.congresar.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun CongresarDatePicker(
    datePickerState: DatePickerState,
    onDismiss: () -> Unit,
    onDateSelected: (Long?) -> Unit,
) {

    DatePickerDialog(
        modifier = Modifier.border(
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
            shape = RoundedCornerShape(16.dp)
        ),
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = { onDateSelected(datePickerState.selectedDateMillis) },
                content = {
                    Text(
                        text = "Ver evento",
                        style = MaterialTheme.typography.titleMediumEmphasized,
                        color = MaterialTheme.colorScheme.background
                    )
                }
            )
        },
        colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.onBackground)
    ) {
        DatePicker(
            colors = DatePickerDefaults.colors(
                disabledDayContentColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                selectedDayContainerColor = MaterialTheme.colorScheme.onBackground,
                todayDateBorderColor = MaterialTheme.colorScheme.onBackground
            ),
            state = datePickerState,
            title = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(color = MaterialTheme.colorScheme.onBackground),
                    contentAlignment = Alignment.Center,

                    ) {
                    Text(
                        text = "Ubicar evento por fecha",
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

            })
    }
}
