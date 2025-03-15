package com.example.layaout.tiempicker

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DateRangePickerDialog(
    onDismiss: () -> Unit,
    onDateRangeSelected: (String, String) -> Unit
) {
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    // DatePickerDialog for Start Date
    val startDatePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            startDate = dateFormat.format(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // DatePickerDialog for End Date
    val endDatePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            endDate = dateFormat.format(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Select Date Range")
        },
        text = {
            Column {
                Button(onClick = { startDatePickerDialog.show() }) {
                    Text(text = if (startDate.isEmpty()) "Pick Start Date" else "Start Date: $startDate")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = { endDatePickerDialog.show() }) {
                    Text(text = if (endDate.isEmpty()) "Pick End Date" else "End Date: $endDate")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
                        onDateRangeSelected(startDate, endDate)
                        onDismiss()
                    }
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun MainScreen() {
    var isDialogOpen by remember { mutableStateOf(false) }
    var selectedRange by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Button(onClick = { isDialogOpen = true }) {
            Text("Open Date Range Picker")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedRange.isNotEmpty()) {
            Text("Selected Range: $selectedRange")
        }

        if (isDialogOpen) {
            DateRangePickerDialog(
                onDismiss = { isDialogOpen = false },
                onDateRangeSelected = { startDate, endDate ->
                    selectedRange = "Start: $startDate, End: $endDate"
                }
            )
        }
    }
}

