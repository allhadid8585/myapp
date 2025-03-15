package com.example.layaout.tiempicker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun DateRangePickerScreen() {
    var isDialogOpen by remember { mutableStateOf(false) }
    var startDate by remember { mutableStateOf<Date?>(null) }
    var endDate by remember { mutableStateOf<Date?>(null) }
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (startDate != null && endDate != null) {
                "Start: ${dateFormatter.format(startDate!!)}\nEnd: ${dateFormatter.format(endDate!!)}"
            } else {
                "Select a date range"
            },
            fontSize = 18.sp,
            modifier = Modifier.padding(16.dp)
        )

        Button(onClick = { isDialogOpen = true }) {
            Text("Select Date Range")
        }

        if (isDialogOpen) {
            DateRangePickerDialog(
                onDismiss = { isDialogOpen = false },
                onDateRangeSelected = { start, end ->
                    startDate = start as Date
                    endDate = end as Date
                    isDialogOpen = false
                }
            )
        }
    }
}