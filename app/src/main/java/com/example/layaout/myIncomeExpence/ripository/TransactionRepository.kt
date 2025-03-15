package com.example.layaout.myIncomeExpence.ripository

import com.example.layaout.myIncomeExpence.dataClasess.Category
import com.example.layaout.myIncomeExpence.dataClasess.Transaction
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val db: FirebaseFirestore
) {
    private val transactionsCollection = db.collection("transactions")
    private val categoriesCollection = db.collection("categories")

    suspend fun getTransactions(): List<Transaction> {
        return try {
            transactionsCollection.get().await()
                .documents.mapNotNull {it.toObject(Transaction::class.java)?.copy(id = it.id) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addTransaction(transaction: Transaction): Boolean {
        return try {
            transactionsCollection.add(transaction).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteTransaction(documentId: String): Boolean {
        return try {
            transactionsCollection.document(documentId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getCategories(): List<Category> {
        return try {
            categoriesCollection.get().await()
                .documents.mapNotNull { it.toObject(Category::class.java)?.copy(id = it.id) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun addCategory(category: Category): Boolean {
        return try {
            categoriesCollection.add(category).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
