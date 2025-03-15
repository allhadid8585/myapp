package com.example.layaout.myIncomeExpence.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.layaout.myIncomeExpence.viewModel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategoryDialog(
    transactionViewModel: TransactionViewModel = hiltViewModel(),
    onDismissRequest: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var isOpen by remember { mutableStateOf(false) }

    val listType = listOf("income", "expense")

    AlertDialog(
        title = { Text(text = "Add Category") },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Name") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = isOpen,
                    onExpandedChange = { isOpen = !isOpen }
                ) {
                    OutlinedTextField(
                        value = type,
                        onValueChange = {},
                        label = { Text(text = "Type") },
                        singleLine = true,
                        trailingIcon = {
                            Icon(
                                imageVector = if (isOpen) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = null)
                        },
                        readOnly = true, // جلوگیری از تایپ دستی
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )

                    ExposedDropdownMenu(
                        expanded = isOpen,
                        onDismissRequest = { isOpen = false },
                        modifier = Modifier.heightIn(max = 200.dp)
                    ) {
                        listType.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    type = item
                                    isOpen = false // بستن منو بعد از انتخاب
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        },
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            Button(
                onClick = {
                    transactionViewModel.addCategory(name, type) // ارسال داده به ViewModel
                    onDismissRequest() // بستن دیالوگ بعد از ثبت
                },
                enabled = name.isNotEmpty() && type.isNotEmpty()
            ) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissRequest() }) {
                Text(text = "Cancel")
            }
        }
    )
}
