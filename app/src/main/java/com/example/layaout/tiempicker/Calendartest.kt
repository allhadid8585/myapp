package com.example.layaout.tiempicker

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun CalendarScreenTest(){
        Modifier.padding().background( if (4== 5) Color.Red else Color.Yellow )
    var calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, 2025)
    calendar.set(Calendar.MONTH, 7)
    calendar.set(Calendar.DAY_OF_MONTH, 1)

    var date = kotlin.collections.mutableListOf<Date?>()
    for (i in 1..6) {
        null  }
    for (i in 1..31) {
        date.add(Date()) }

    Log.d("CalengarTime", "${
        date.chunked(7)
        
        
    }")
}