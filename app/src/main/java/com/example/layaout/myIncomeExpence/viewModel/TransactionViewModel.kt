package com.example.layaout.myIncomeExpence.viewModel

import android.icu.util.LocaleData
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.layaout.myIncomeExpence.dataClasess.Category
import com.example.layaout.myIncomeExpence.dataClasess.Transaction
import com.example.layaout.myIncomeExpence.ripository.TransactionRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories



    private val listenerRegistrations = mutableListOf<ListenerRegistration>()  // نگه‌داری لیسنرها

    private var isTransactionListenerActive = false

    private fun fetchTransactions() {
        if (isTransactionListenerActive) return  // جلوگیری از اجرای چندباره
        isTransactionListenerActive = true

        val transactionListener = firestore.collection("transactions")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("Firestore", "Error fetching transactions", error)
                    return@addSnapshotListener
                }

                val transactionList = value?.documents?.mapNotNull { it.toObject(Transaction::class.java)?.copy(id = it.id )} ?: emptyList()
                _transactions.value = transactionList
            }

        listenerRegistrations.add(transactionListener)
    }

    private var isCategoryListenerActive = false

    private fun fetchCategories() {
        if (isCategoryListenerActive) return
        isCategoryListenerActive = true

        val categoryListener = firestore.collection("categories")
            .addSnapshotListener{value, error ->
                if (error != null){
                    Log.e("Firestore", "Error fetching categories")
                    return@addSnapshotListener
                }

                val categoriesList = value?.documents?.mapNotNull { it.toObject(Category::class.java)?.copy(id = it.id) } ?: emptyList()
                _categories.value = categoriesList
            }
        listenerRegistrations.add(categoryListener)
    }

    init {
        fetchCategories()
        fetchTransactions()
    }

    fun addTransaction(amount: Double, categoryName: String, description: String) {
        val category = categories.value.find { it.name == categoryName }
        if (category != null) {
            val transaction = Transaction(
                amount = amount,
                categoryId = category.id,
                type = category.type,
                description = description,
            )
            viewModelScope.launch {
                repository.addTransaction(transaction)
            }
        } else {
            // مدیریت خطا: دسته‌بندی یافت نشد
            println("Error: Category not found")
        }
    }

    fun addCategory(name: String, type: String) {
        val existingCategory = categories.value.find { it.name == name }
        if (existingCategory == null) {
            val newCategory = Category(
                id = System.currentTimeMillis().toString(), // ایجاد ID تصادفی
                name = name,
                type = type
            )
            viewModelScope.launch {
                try {
                    repository.addCategory(newCategory)
                } catch (e: Exception) {
                    Log.e("addCategory", "Error adding category: ${e.message}")
                }
            }
        } else {
            Log.e("addCategory","Error: Category already exists")
        }
    }

    fun deleteTransaction(documentId: String){

        viewModelScope.launch {
            repository.deleteTransaction(documentId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        listenerRegistrations.forEach{it.remove()}
    }
}

