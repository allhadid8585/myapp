package com.example.layaout.myIncomeExpence.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale


@SuppressLint("SimpleDateFormat")
@Composable
fun DateRangePickerDialog(
    onDismissRequest: () -> Unit,
    onDateRangeSelected: (Date?, Date?) -> Unit
) {
    var selectedStartDate by remember { mutableStateOf<Date?>(null) }
    var selectedEndDate by remember { mutableStateOf<Date?>(null) }
    var currentMonth by remember { mutableStateOf(Calendar.getInstance().get(Calendar.MONTH)) }
    var currentYear by remember { mutableStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    var iconBtn by remember {
        mutableStateOf(true)
    }

    val calendar = Calendar.getInstance()
    when{
        selectedStartDate != null && selectedEndDate == null -> {
            calendar.time = selectedStartDate as Date
            currentYear = calendar.get(Calendar.YEAR)
            currentMonth = calendar.get(Calendar.MONTH)
        }

        selectedStartDate == null && selectedEndDate != null -> {
            calendar.time = selectedEndDate as Date
            currentYear = calendar.get(Calendar.YEAR)
            currentMonth = calendar.get(Calendar.MONTH)
        }
        selectedStartDate != null && selectedEndDate != null -> {
            calendar.time = selectedEndDate as Date
            currentYear = calendar.get(Calendar.YEAR)
            currentMonth = calendar.get(Calendar.MONTH)
        }

    }

    fun dateChenger (start: Date?, end: Date?): Pair<Date?, Date?>{
        return Pair(end, start)
    }

    if (selectedStartDate != null && selectedEndDate != null){
        if (selectedEndDate!!.before(selectedStartDate)){
            val (startDate, endDate) = dateChenger(selectedStartDate, selectedEndDate)
            selectedStartDate = startDate
            selectedEndDate = endDate
        }
    }

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        title = {
            Column ( modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Select Date Range", fontSize = 20.sp,
                    modifier = Modifier
                )
            }
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // کنترل ماه و سال

                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                    val (txt1, txt2, btn1) = createRefs()

                    Text(text = if (selectedStartDate != null)selectedStartDate?.let {
                        SimpleDateFormat("MMM dd, yyyy").format(
                            it
                        )
                    }.toString() else "START DATE",
                        fontSize = 25.sp,
                        modifier = Modifier.constrainAs(txt1){
                            top.linkTo(parent.top)
                            start.linkTo(parent.start, margin = 10.dp)
                            bottom.linkTo(txt2.top)
                        }
                    )
                    Text(text = if (selectedEndDate != null)selectedEndDate?.let {
                        SimpleDateFormat("MMM dd, yyyy").format(
                            it
                        )
                    }.toString() else "END DATE",
                        fontSize = 25.sp,
                        modifier = Modifier.constrainAs(txt2){
                            top.linkTo(txt1.bottom)
                            start.linkTo(parent.start, margin = 10.dp)
                            bottom.linkTo(parent.bottom)
                        }
                    )

                    IconButton(
                        onClick = { iconBtn = !iconBtn },
                        Modifier.constrainAs(btn1){
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                    ) {
                        Icon(imageVector = if (iconBtn) Icons.Default.Create else Icons.Default.DateRange,
                            contentDescription = null,
                        )
                    }
                }
                when{
                    !iconBtn ->
                        DateRangeTextField(
                            selectedStartDate = selectedStartDate,
                            selectedEndDate = selectedEndDate ,
                            onDateRangeFieldSelected = {start, end ->
                                selectedStartDate = start
                                selectedEndDate = end
                            }
                        )
                    else ->
                        Column {
                            ConstraintLayout(
                                modifier = Modifier.fillMaxWidth()
                            ) {

                                val (txt1,txt2,btn1,btn2) = createRefs()

                                Text(
                                    text = SimpleDateFormat("MMMM", Locale.getDefault()).format(
                                        Calendar.getInstance().apply {
                                            set(Calendar.YEAR, currentYear)
                                            set(Calendar.MONTH, currentMonth)
                                        }.time
                                    ),
                                    fontSize = 20.sp,
                                    modifier = Modifier.constrainAs(txt1){
                                        start.linkTo(parent.start, margin = 12.dp)
                                        top.linkTo(parent.top)
                                        bottom.linkTo(parent.bottom)
                                    }
                                )

                                Text(
                                    text = SimpleDateFormat("yyyy", Locale.getDefault()).format(
                                        Calendar.getInstance().apply {
                                            set(Calendar.YEAR, currentYear)
                                            set(Calendar.MONTH, currentMonth)
                                        }.time
                                    ),
                                    fontSize = 20.sp,
                                    modifier = Modifier.constrainAs(txt2){
                                        start.linkTo(txt1.end, margin = 15.dp)
                                        top.linkTo(parent.top)
                                        bottom.linkTo(parent.bottom)
                                    }
                                )

                                Text("<",
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .constrainAs(btn1) {
                                            end.linkTo(btn2.start, margin = 10.dp)
                                            top.linkTo(parent.top)
                                            bottom.linkTo(parent.bottom)
                                        }
                                        .clickable {
                                            if (currentMonth == 0) {
                                                currentMonth = 11
                                                currentYear -= 1
                                            } else {
                                                currentMonth -= 1
                                            }
                                        }
                                        .padding(10.dp)
                                )
                                Text(">",
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .constrainAs(btn2) {
                                            end.linkTo(parent.end)
                                            top.linkTo(parent.top)
                                            bottom.linkTo(parent.bottom)
                                        }
                                        .clickable {
                                            if (currentMonth == 11) {
                                                currentMonth = 0
                                                currentYear += 1
                                            } else {
                                                currentMonth += 1
                                            }
                                        }
                                        .padding(10.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))

                            // نمایش تقویم
                            CalendarGrid(
                                year = currentYear,
                                month = currentMonth,
                                selectedStartDate = selectedStartDate,
                                selectedEndDate = selectedEndDate,
                                onDateSelected = { date ->
                                    when {
                                        selectedStartDate == null -> selectedStartDate = date
                                        selectedEndDate == null && date.after(selectedStartDate) -> selectedEndDate = date
                                        selectedEndDate == null && date.before(selectedStartDate) -> {
                                            selectedEndDate = selectedStartDate
                                            selectedStartDate = date
                                        }
                                        else -> {
                                            selectedStartDate = date
                                            selectedEndDate = null
                                        }
                                    }
                                }
                            )
                        }

                }



            }
        },
        confirmButton = {
            when{
                !iconBtn ->
                    Button(
                        onClick = {
                            iconBtn = true
                        },
                        enabled = selectedStartDate != null && selectedEndDate != null
                    ) {
                        Text("Confirm")
                    }
                else ->
                    Button(
                        onClick = {
                            if (selectedStartDate != null) {
                                onDateRangeSelected(selectedStartDate, selectedEndDate)
                                onDismissRequest()
                            }
                        },
                        enabled = selectedStartDate != null
                    ) {
                        Text("Confirm")
                    }
            }

        },
        dismissButton = {
            when{
                !iconBtn ->
                    TextButton(onClick = { iconBtn = true }) {
                        Text("Cancel")
                    }
                else ->
                    TextButton(onClick = { onDismissRequest() }) {
                        Text("Cancel")
                    }
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangeTextField(
    selectedStartDate: Date?,
    selectedEndDate: Date?,
    onDateRangeFieldSelected: (Date?, Date?) -> Unit
) {
    Column(verticalArrangement = Arrangement.Center) {

        fun formatDateInput(input: String): TextFieldValue {
            val cleanInput = input.filter { it.isDigit() } // فقط اعداد مجاز هستند
            val sb = StringBuilder()

            for (i in cleanInput.indices) {
                sb.append(cleanInput[i])
                if ((i == 1 || i == 3) && i < cleanInput.lastIndex) {
                    sb.append('/')
                }
            }

            // جلوگیری از اضافه شدن صفر اضافی در سال
            val formattedText = sb.toString()

            return TextFieldValue(text = formattedText, selection = TextRange(formattedText.length))
        }

        fun validateDate(input: String): Date? {
            if (input.length < 10) return null // فقط زمانی پردازش شود که طول کامل شده باشد

            return try {
                val parts = input.split("/")
                if (parts.size == 3) {
                    val day = parts[0].toInt()
                    val month = parts[1].toInt()
                    val year = parts[2].toInt()

                    if (day in 1..31 && month in 1..12 && year > 999) {
                        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        formatter.isLenient = false
                        formatter.parse(input)
                    } else null
                } else null
            } catch (e: Exception) {
                null
            }
        }

        var startDateInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(text = "", selection = TextRange(0)))
        }
        var endDateInput by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(text = "", selection = TextRange(0)))
        }

        LaunchedEffect(selectedStartDate) {
            selectedStartDate?.let {
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
                if (startDateInput.text != formattedDate) {
                    startDateInput = TextFieldValue(formattedDate, TextRange(formattedDate.length))
                }
            }
        }

        LaunchedEffect(selectedEndDate) {
            selectedEndDate?.let {
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
                if (endDateInput.text != formattedDate) {
                    endDateInput = TextFieldValue(formattedDate, TextRange(formattedDate.length))
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        // **فیلد ورود تاریخ شروع**
        OutlinedTextField(
            value = startDateInput,
            onValueChange = { newValue ->
                if (newValue.text.length <= 10) {
                    startDateInput = formatDateInput(newValue.text)
                }
            },
            label = { Text(text = "Start Date") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true
        )
        if(validateDate(startDateInput.text)==null && startDateInput.text.length== 10){
            Text(text = "Your Input Date isn't correct")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // **فیلد ورود تاریخ پایان**
        OutlinedTextField(
            value = endDateInput,
            onValueChange = { newValue ->
                if (newValue.text.length <= 10) {
                    endDateInput = formatDateInput(newValue.text)
                }
            },
            label = { Text(text = "End Date") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true
        )
        if(validateDate(endDateInput.text)==null && endDateInput.text.length== 10){
            Text(text = "Your Input Date isn't correct")
        }

        Spacer(modifier = Modifier.height(16.dp))

        val parsedStartDate by remember { derivedStateOf { validateDate(startDateInput.text) } }
        val parsedEndDate by remember { derivedStateOf { validateDate(endDateInput.text) } }

        LaunchedEffect(parsedStartDate, parsedEndDate) {
            onDateRangeFieldSelected(parsedStartDate, parsedEndDate)
        }
    }
}


@Composable
fun CalendarGrid(
    year: Int,
    month: Int,
    selectedStartDate: Date?,
    selectedEndDate: Date?,
    onDateSelected: (Date) -> Unit
) {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month)
    calendar.set(Calendar.DAY_OF_MONTH, 1)

    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1  // شروع ماه از چه روزی است (0 برای یکشنبه)

    val dates = mutableListOf<Date?>()

    // اضافه کردن روزهای خالی برای تکمیل هفته اول
    repeat(firstDayOfWeek) {
        dates.add(null)
    }

    // اضافه کردن روزهای ماه
    for (day in 1..daysInMonth) {
        calendar.set(Calendar.DAY_OF_MONTH, day)
        dates.add(calendar.time)
    }

    // اضافه کردن روزهای خالی برای تکمیل هفته آخر (تا ۶ هفته)
    while (dates.size % 7 != 0) {
        dates.add(null)
    }

    // اطمینان حاصل شود که همیشه تقویم دارای ۶ هفته (42 سلول) باشد
    while (dates.size < 42) {
        dates.add(null)
    }

    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    Column {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 13.dp, end = 3.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Text(text = "S", fontSize = 16.sp, modifier = Modifier.size(40.dp))
            Text(text = "M", fontSize = 16.sp, modifier = Modifier.size(40.dp))
            Text(text = "T", fontSize = 16.sp, modifier = Modifier.size(40.dp))
            Text(text = "W", fontSize = 16.sp, modifier = Modifier.size(40.dp))
            Text(text = "T", fontSize = 16.sp, modifier = Modifier.size(40.dp))
            Text(text = "F", fontSize = 16.sp, modifier = Modifier.size(40.dp))
            Text(text = "S", fontSize = 16.sp, modifier = Modifier.size(40.dp))
        }
        dates.chunked(7).forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                week.forEach { date ->
                    val isStartDate = selectedStartDate != null && date != null &&
                            dateFormat.format(date) == dateFormat.format(selectedStartDate)
                    val isEndDate = selectedEndDate != null && date != null &&
                            dateFormat.format(date) == dateFormat.format(selectedEndDate)
                    val isInRange = selectedStartDate != null && selectedEndDate != null &&
                            date != null && date.after(selectedStartDate) && date.before(selectedEndDate)

                    // رنگ پس‌زمینه بر اساس وضعیت انتخاب‌شده
                    val backgroundColor = when {
                        isStartDate || isEndDate -> Color.Red // رنگ قرمز برای تاریخ شروع و پایان
                        isInRange -> Color.LightGray // رنگ آبی برای بازه‌ی انتخابی
                        else -> Color.Transparent
                    }

                    val rangeBackgroundColor = if (isInRange or isEndDate) Color.LightGray else Color.Transparent

                    // شکل نمایش دایره‌ای برای Start/End و مستطیل برای Range
                    val shape = when {
                        isStartDate || isEndDate -> RoundedCornerShape(50) // دایره برای استارت و اند
                        isInRange -> RoundedCornerShape(0) // مستطیل برای بازه
                        else -> RoundedCornerShape(0)
                    }

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(rangeBackgroundColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(shape)
                                .background(backgroundColor)
                                .clickable(enabled = date != null) {
                                    date?.let { onDateSelected(it) }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = date?.let { SimpleDateFormat("d", Locale.getDefault()).format(it) } ?: "", fontSize = 15.sp)
                        }
                    }
                }
            }
        }
    }
}
