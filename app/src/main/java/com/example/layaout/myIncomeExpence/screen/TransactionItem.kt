package com.example.layaout.myIncomeExpence.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.layaout.myIncomeExpence.dataClasess.Transaction
import com.example.layaout.myIncomeExpence.viewModel.TransactionViewModel
import com.example.layaout.ui.theme.Green
import com.example.layaout.ui.theme.Red


@Composable
fun TransactionItem(
    transaction: Transaction,
    transactionViewModel: TransactionViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val categoryList = transactionViewModel.categories.collectAsState(emptyList())

    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${transaction.amount}",
                        color = if (transaction.type == "income") Green else Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        modifier = Modifier.padding(end = 50.dp)
                    )

                    Text(
                        text = categoryList.value.find { it.id == transaction.categoryId }?.name ?: "UNKNOWN",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }


            if (transaction.description.isNotEmpty()) {
                var isOpen by remember {
                    mutableStateOf(false)
                }
                Column {
                    Text(
                        text = "\uD83D\uDCDD Description: ",
                        fontSize = 18.sp,
                        modifier = Modifier.clickable { isOpen = !isOpen }
                    )
                    if (isOpen) {
                        Text(
                            text = transaction.description,
                            modifier = Modifier.padding(top = 8.dp),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

