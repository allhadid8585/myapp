@file:Suppress("DEPRECATION")

package com.example.layaout.myIncomeExpence.screen

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.layaout.myIncomeExpence.helperFunctions.changeLanguage
import com.example.layaout.myIncomeExpence.helperFunctions.toEndOfDay
import com.example.layaout.myIncomeExpence.helperFunctions.toEndOfMonth
import com.example.layaout.myIncomeExpence.helperFunctions.toStartOfDay
import com.example.layaout.myIncomeExpence.helperFunctions.toStartOfMonth
import com.example.layaout.myIncomeExpence.viewModel.TransactionViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SimpleDateFormat")
@Composable
fun ExpanseScreen(
    navController: NavController,
    transactionViewModel: TransactionViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope()

    var isRefreshing by remember {
        mutableStateOf(false)
    }


    key (isRefreshing) {


        val currentDate = Date()

        val formatter = SimpleDateFormat("dd MMM yyyy")

        var startDate by remember {
            mutableStateOf<Date?>(null)
        }


        var endDate by remember {
            mutableStateOf<Date?>(null)
        }

        val categoryList = transactionViewModel.categories.collectAsState().value
            .filter { it.type == "expense" }
        var categoryIsOpen by remember {
            mutableStateOf(false)
        }

        var categoryId by remember {
            mutableStateOf<String?>(null)
        }
        var categoryName by remember {
            mutableStateOf("ALL")
        }

        val transactions = transactionViewModel.transactions.collectAsState().value
            .filter { it.type == "expense" }
            .filter { it.date != null }
            .filter {
                when {
                    startDate != null && endDate == null -> (it.date!!.after(startDate?.toStartOfDay()) || it.date == startDate?.toStartOfDay()) && (it.date.before(
                        startDate?.toEndOfDay()
                    ) || it.date == startDate?.toEndOfDay())

                    startDate != null && endDate != null -> (it.date!!.after(startDate?.toStartOfDay()) || it.date == startDate?.toStartOfDay()) && (it.date.before(
                        endDate?.toEndOfDay()
                    ) || it.date == endDate?.toEndOfDay())

                    else -> it.type == "income" || it.type == "expense"
                }
            }
            .filter {
                when {
                    categoryName != "ALL" && categoryId != null ->
                        it.categoryId == categoryId

                    else ->
                        it.type == "income" || it.type == "expense"
                }
            }


        val totalIncome = if (transactions.isNotEmpty()) transactions.filter { it.type == "income" }
            .sumOf { it.amount } else 0.0
        val totalExpanse =
            if (transactions.isNotEmpty()) transactions.filter { it.type == "expense" }
                .sumOf { it.amount } else 0.0
        val sumOfTransaction = totalIncome - totalExpanse


        val transactionsGrouped =
            transactions.sortedBy { it.date }.groupBy { it.date?.toStartOfDay() }.toSortedMap(
                compareByDescending { it })


        val activity = LocalContext.current as Activity




        LaunchedEffect(transactionsGrouped) {

            val minDate = transactionsGrouped.keys.minByOrNull { it as Date }

            val maxDate = transactionsGrouped.keys.maxByOrNull { it as Date }

            if (startDate == null) {
                startDate = currentDate.toStartOfMonth()
            }
            if (endDate == null) {
                endDate = currentDate.toEndOfMonth()
            }
        }








        Column(
            modifier = Modifier.fillMaxSize()
        ) {




            ConstraintLayout(
                Modifier
                    .height(150.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                    )
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                val (txtSum, income, expense, btnEng, btnRus, btnTjk) = createRefs()

                Text(
                    text = "ENG",
                    modifier = Modifier
                        .constrainAs(btnEng) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end, margin = 2.dp)
                        }
                        .width(50.dp)
                        .height(28.dp)
                        .clip(RoundedCornerShape(10.dp))

                        .background(Color.LightGray)
                        .padding(5.dp)
                        .clickable { changeLanguage(activity, "en") },
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "RUS",
                    modifier = Modifier
                        .constrainAs(btnRus) {
                            top.linkTo(parent.top)
                            end.linkTo(btnEng.start, margin = 5.dp)
                        }
                        .width(50.dp)
                        .height(28.dp)
                        .clip(RoundedCornerShape(10.dp))

                        .background(Color.LightGray)
                        .padding(5.dp)
                        .clickable { changeLanguage(activity, "ru") },
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "TJK",
                    modifier = Modifier
                        .constrainAs(btnTjk) {
                            top.linkTo(parent.top)
                            end.linkTo(btnRus.start, margin = 5.dp)
                        }
                        .width(50.dp)
                        .height(28.dp)
                        .clip(RoundedCornerShape(10.dp))

                        .background(Color.LightGray)
                        .padding(5.dp)
                        .clickable { changeLanguage(activity, "tg") },
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )



                Text(
                    text = String.format("%.2f", totalExpanse),
                    fontSize = 35.sp,
                    modifier = Modifier
                        .constrainAs(txtSum) {
                            top.linkTo(btnEng.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .padding(bottom = 8.dp),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

            }

            Spacer(modifier = Modifier.height(10.dp))


            var datePickerIsOpen by remember {
                mutableStateOf(false)
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .zIndex(1f)

            ) {

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { datePickerIsOpen = true }
                        .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 10.dp)


                ) {
                    Text(
                        text = when {
                            startDate != null && endDate == null ->
                                formatter.format(startDate as Date)

                            startDate != null && endDate != null ->
                                formatter.format(startDate as Date) + " - " + formatter.format(endDate as Date)

                            else ->
                                formatter.format(currentDate)
                        },
                        modifier = Modifier,
                        fontSize = 20.sp
                    )

                    Image(imageVector = Icons.Default.DateRange, contentDescription = null)

                    if (datePickerIsOpen) {
                        DateRangePickerDialog(onDismissRequest = {
                            datePickerIsOpen = false
                        }) { start, end ->
                            startDate = start
                            endDate = end
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))

                ExposedDropdownMenuBox(
                    expanded = categoryIsOpen,
                    onExpandedChange = { categoryIsOpen = !categoryIsOpen },
                    modifier = Modifier
                        .width(300.dp)
                        .padding(15.dp)


                ) {
                    Text(
                        text = categoryName,

                        modifier = Modifier
                            .menuAnchor()
                            .width(300.dp),
                        fontSize = 20.sp

                    )

                    ExposedDropdownMenu(
                        expanded = categoryIsOpen,
                        onDismissRequest = { categoryIsOpen = false },
                        modifier = Modifier.heightIn(max = 200.dp)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "ALL",
                                    fontSize = 20.sp
                                )
                            },
                            onClick = {
                                categoryName = "ALL"
                                categoryId = null
                                categoryIsOpen = false
                            })
                        categoryList.forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = item.name,
                                        fontSize = 20.sp
                                    )
                                },
                                onClick = {
                                    categoryId = item.id
                                    categoryName = item.name
                                    categoryIsOpen = false // بستن منو بعد از انتخاب
                                }
                            )
                        }
                    }
                }}







            suspend fun refresh() {
                isRefreshing = true
                delay(2000)
                isRefreshing = false
            }

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                onRefresh = {
                    scope.launch {
                        refresh()
                    }

                }) {

                NativeChatList(transactions = transactions, modifier = Modifier
                    .fillMaxWidth()
                )

            }



        }
    }
}

