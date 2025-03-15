package com.example.layaout.tiempicker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

data class Item(
    val name: String = "",
    val createdAt: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now()
)

@Composable
fun FilterItemsScreen() {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    var startDateTime by remember { mutableStateOf("") }
    var endDateTime by remember { mutableStateOf("") }
    var items by remember { mutableStateOf(listOf<Item>()) }
    var error by remember { mutableStateOf<String?>(null) }

    val calendar = Calendar.getInstance()

    // DatePickerDialog for Start Date and Time
    val startDatePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val date = formatter.format(calendar.time)
            startDateTime = date
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val startTimePickerDialog = TimePickerDialog(
        LocalContext.current,
        { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            val time = formatter.format(calendar.time)
            startDateTime = time
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    // DatePickerDialog for End Date and Time
    val endDatePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val date = formatter.format(calendar.time)
            endDateTime = date
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    val endTimePickerDialog = TimePickerDialog(
        LocalContext.current,
        { _, hour, minute ->
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            val time = formatter.format(calendar.time)
            endDateTime = time
        },
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        true
    )

    Column(modifier = Modifier.padding(16.dp)) {
        TextButton(onClick = { startDatePickerDialog.show() }) {
            Text("Pick Start Date")
        }

        TextButton(onClick = { startTimePickerDialog.show() }) {
            Text("Pick Start Time")
        }

        Text("Start DateTime: $startDateTime", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { endDatePickerDialog.show() }) {
            Text("Pick End Date")
        }

        TextButton(onClick = { endTimePickerDialog.show() }) {
            Text("Pick End Time")
        }

        Text("End DateTime: $endDateTime", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            try {
                val startTime = Timestamp(formatter.parse(startDateTime)!!)
                val endTime = Timestamp(formatter.parse(endDateTime)!!)

            //    fetchFilteredItems(
                //       startTime,
                //  endTime,
                //  onSuccess = { fetchedItems ->
                //      items = fetchedItems
                //      error = null
                //  },
                //  onError = { exception ->
                //           error = exception.message
                //   }
                //   )
            } catch (e: Exception) {
                error = "Invalid date format!"
            }
        }) {
            Text("Filter Items")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (error != null) {
            Text("Error: $error", color = MaterialTheme.colorScheme.error)
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items) { item ->
                Text("Item: ${item.name}, Date: ${item.createdAt.toDate()}")
            }
        }
    }

}






