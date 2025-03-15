package com.example.layaout.myIncomeExpence.dataClasess

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ServerTimestamp
import java.sql.Timestamp
import java.util.Date

data class Transaction(
    val id: String = "",
    val amount: Double = 0.0,
    val categoryId: String = "",
    val type: String = "",
    val description: String = "",
    @ServerTimestamp val date: Date? = null
)
